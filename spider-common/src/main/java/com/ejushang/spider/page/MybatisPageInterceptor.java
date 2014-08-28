package com.ejushang.spider.page;

import com.ejushang.spider.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * Mybatis分页拦截器
 * 当mapper方法只有一个参数并且参数是Page类型,或者多个参数但是有一个@Param("page) Page page参数,
 * 该拦截器会拦截该mapper方法调用,查询出总条数以及根据page的start和limit自动修改查询语句进行分页查询,
 * 查询出来的总条数以及分页查询结果将放入page对象
 *
 * 分页用法:
 * 构造Page对象,接受从前端传入的参数,例如:
 * // 构造分页信息
 * Page page = new Page();
 * // 设置当前页
 * page.setPageNo(storageQuery.getPage());
 * // 设置分页大小
 * page.setPageSize(storageQuery.getLimit());
 *
 * mapper方法传入分页对象,mapper方法的名字最好以ByPage结尾
 * 例如:
 * public List<Storage> findJoinStorageByPage(Page page);//单个参数
 * public List<Storage> findJoinStorageByPage(@Param("storage")StorageQuery storage, @Param("page")Page page);//多个参数
 * 如果是多个参数,注意xml文件中sql语句的写法,引用属性名字的时候需要级联引用.例如:
 * <if test="storage.repoId != null ">
 * and r.id = #{storage.repoId}
 * </if>
 * 并且xml文件的sql不再需要limit语句
 *
 * 一旦方法执行完成,查询结果和总条数已经被设置到page对象里了,直接使用即可,例如:
 * page.getTotalCount()//返回总条数
 * page.getResult()//返回查询结果
 *
 * 具体用法可以参考StorageService.findJoinStorage
 *
 * User: liubin
 * Date: 14-1-16
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
})
public class MybatisPageInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(MybatisPageInterceptor.class);

    private Properties properties;

    private static final String SQL_SOURCE_FIELD_NAME = "sqlSource";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object parameterObject = invocation.getArgs()[1];
        Page page = null;
        //当mapper方法只有一个参数并且参数是Page类型,或者多个参数但是有一个@Param("page) Page page参数的时候才进行拦截
        if(parameterObject instanceof Page) {
            page = (Page)parameterObject;
        } else if(parameterObject instanceof Map) {
            Map paramsMap = (Map)parameterObject;
            if(paramsMap.containsKey("page")) {
                page = (Page)paramsMap.get("page");
            }
        }
        if(page == null) return invocation.proceed();

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        //原sql语句
        String beforePaginationSql = boundSql.getSql().trim();
        log.debug("beforePaginationSql:" + beforePaginationSql);
        //查询总条数的sql语句
        String countSql = buildCountSql(beforePaginationSql);
        log.debug("countSql:" + countSql.toString());
        int totalCount = 0;
        Connection connection=getConnection(((Executor) invocation.getTarget()).getTransaction(), mappedStatement.getStatementLog());
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            //进行总条数查询
            countStmt = connection.prepareStatement(countSql.toString());
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql.toString(), boundSql.getParameterMappings(), parameterObject);
            setParameters(countStmt, mappedStatement, countBS, parameterObject);
            rs = countStmt.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(countStmt != null) {
                countStmt.close();
            }
        }

        page.setTotalCount(totalCount);
        log.debug("totalCount: " + totalCount);
        //修改后的sql语句
        String afterPaginationSql = beforePaginationSql + String.format(" limit %d,%d ", page.getStart(), page.getPageSize());
        log.debug("afterPaginationSql:" + afterPaginationSql);
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), afterPaginationSql, boundSql.getParameterMappings(), boundSql.getParameterObject());

//        !存在并发问题!
//        //首先拿到原始的SqlSource为了之后恢复用,然后修改MapperStatement的sqlSource
//        SqlSource originalSqlSource = (SqlSource)FieldUtils.readField(mappedStatement, SQL_SOURCE_FIELD_NAME, true);
//        FieldUtils.writeField(mappedStatement, SQL_SOURCE_FIELD_NAME, new BoundSqlSqlSource(newBoundSql), true);
//
//        page.setResult((List)value);
//        //还原现场
//        FieldUtils.writeField(mappedStatement, SQL_SOURCE_FIELD_NAME, originalSqlSource, true);

        MappedStatement newMappedStatement = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
        invocation.getArgs()[0]= newMappedStatement;

        //执行查询
        Object value = invocation.proceed();
        page.setResult((List)value);

        return value;
    }

    private String buildCountSql(String sql) {
        StringBuilder countSql = new StringBuilder("select count(1) ").append(
                sql.substring(StringUtils.indexOfIgnoreCase(sql, "from"), sql.length()));
        if(StringUtils.indexOfIgnoreCase(sql, "group by") == -1) {
            return countSql.toString();
        } else {
            //解决group by分页问题
            return new StringBuilder("select count(1) from ( ").append(countSql).append(" ) count_all_t ").toString();
        }
    }

    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
        new DefaultParameterHandler(mappedStatement, parameterObject, boundSql).setParameters(ps);
    }


    /**
     * 参考BaseExecutor的getConnection方法
     * @param transaction
     * @param statementLog
     * @return
     * @throws SQLException
     */
    private Connection getConnection(Transaction transaction, Log statementLog) throws SQLException {
        Connection connection = transaction.getConnection();
        if (statementLog.isDebugEnabled()) {
            return ConnectionLogger.newInstance(connection, statementLog);
        } else {
            return connection;
        }
    }

    @Override
    public Object plugin(Object target) {
        //判断target有没有实现executor,如果没有实现这个接口放弃拦截,优化性能
        if(target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms,
                                                    SqlSource newSqlSource) {

        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(ms.getConfiguration(),
                ms.getId(), newSqlSource, ms.getSqlCommandType());
        statementBuilder.resource(ms.getResource());
        statementBuilder.fetchSize(ms.getFetchSize());
        statementBuilder.statementType(ms.getStatementType());
        statementBuilder.keyGenerator(ms.getKeyGenerator());
        statementBuilder.keyProperty(StringUtils.join(ms.getKeyProperties(), ','));
        statementBuilder.keyColumn(StringUtils.join(ms.getKeyColumns(), ','));
        statementBuilder.databaseId(ms.getDatabaseId());
        statementBuilder.lang(ms.getLang());
        statementBuilder.resultOrdered(ms.isResultOrdered());
        statementBuilder.resulSets(StringUtils.join(ms.getResulSets(), ","));
        statementBuilder.timeout(ms.getTimeout());
        statementBuilder.parameterMap(ms.getParameterMap());
        statementBuilder.resultMaps(ms.getResultMaps());
        statementBuilder.cache(ms.getCache());

        MappedStatement newMs = statementBuilder.build();
        return newMs;
    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
