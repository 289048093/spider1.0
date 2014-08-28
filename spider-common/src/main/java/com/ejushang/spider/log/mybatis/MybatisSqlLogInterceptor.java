package com.ejushang.spider.log.mybatis;

import com.ejushang.spider.domain.SqlLog;
import com.ejushang.spider.log.BusinessLogHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis拦截器,记录所有sql语句的执行
 *
 * User: liubin
 * Date: 14-1-13
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })
})
public class MybatisSqlLogInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(MybatisSqlLogInterceptor.class);

    private Properties properties;

    private BusinessLogHolder businessLogHolder = BusinessLogHolder.getInstance();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //当前没有对象BusinessLog对象,可能请求不是通过页面发起,不需要记录sql日志
        boolean logEnabled = (businessLogHolder.getCurrentBusinessLog() != null);
        if(!logEnabled) {
            return invocation.proceed();
        }

        String sqlMapperId = null;
        BoundSql boundSql = null;
        Configuration configuration = null;
        long start = 0L;
        boolean error = false;
        MappedStatement mappedStatement = null;

        try {
            mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            sqlMapperId = mappedStatement.getId();
            //得到要执行的BoundSql对象
            boundSql = mappedStatement.getBoundSql(parameter);
            configuration = mappedStatement.getConfiguration();
            start = System.currentTimeMillis();
        } catch (Exception e) {
            log.error("记录sql日志的时候发生错误", e);
            error = true;
        }

        Object returnValue = invocation.proceed();

        try {
            if(!error) {
                SqlCommandType operationType = mappedStatement.getSqlCommandType();
                if(!operationType.equals(SqlCommandType.SELECT)) {
                    //非select语句才记录
                    SqlLog sqlLog = new SqlLog();
                    sqlLog.setExecutionTime(System.currentTimeMillis() - start);
                    sqlLog.setContent(getSql(configuration, boundSql));
                    sqlLog.setSqlMapper(sqlMapperId);
                    sqlLog.setOperationType(operationType.toString());

                    //将SqlLog对象加入ThreadLocal,由springmvc的interceptor在返回的时候统一保存到数据库
                    businessLogHolder.addSqlLog(sqlLog);
                }
            }
        } catch (Exception e) {
            log.error("记录sql日志的时候发生错误", e);
        }

        return returnValue;
    }


    /**
     * 打印参数对象
     * @param obj
     * @return
     */
    private String getParameterValue(Object obj) {
        String value = "";
        if(obj != null) {
            if (obj instanceof String) {
                value = "'" + obj.toString() + "'";
            } else if (obj instanceof Date) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                value = "'" + formatter.format(new Date()) + "'";
            } else {
                value = obj.toString();
            }
        }
        return value;
    }

    /**
     * 将请求的参数替换预处理sql语句中的"?",并返回
     * @param configuration
     * @param boundSql
     * @return
     */
    private String getSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.isEmpty() || parameterObject == null) {
            return sql;
        }
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
        } else {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            for (ParameterMapping parameterMapping : parameterMappings) {
                String propertyName = parameterMapping.getProperty();
                if (metaObject.hasGetter(propertyName)) {
                    Object obj = metaObject.getValue(propertyName);
                    sql = sql.replaceFirst("\\?", getParameterValue(obj));
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    Object obj = boundSql.getAdditionalParameter(propertyName);
                    sql = sql.replaceFirst("\\?", getParameterValue(obj));
                }
            }
        }
        return sql;
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
}