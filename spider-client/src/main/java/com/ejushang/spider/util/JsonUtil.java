package com.ejushang.spider.util;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * User: amos.zhou
 * Date: 13-12-23
 * Time: 下午12:10
 * Json工具类
 */
public class JsonUtil {

    public static String objectToJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createJsonGenerator(sw);
            mapper.writeValue(gen, o);
        } catch (IOException e) {
            throw new RuntimeException("不能序列化对象为Json", e);
        } finally {
            if (null != gen) {
                try {
                    gen.close();
                } catch (IOException e) {
                    throw new RuntimeException("不能序列化对象为Json", e);
                }
            }
        }
        return sw.toString();
    }

    /**
     * 将 json 字段串转换为 对象.
     *
     * @param json  字符串
     * @param clazz 需要转换为的类
     * @return
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("将 Json 转换为对象时异常,数据是:" + json, e);
        }
    }

    /**
     *   将 json 字段串转换为 List.
     * @param json
     * @param classz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> jsonToList(String json,Class<T> classz) throws IOException {
        List<T> list = new ObjectMapper().readValue(json, new TypeReference<List<T>>(){});
        return list;
    }


    /**
     *  将 json 字段串转换为 数据.
     * @param json
     * @param classz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T>  T[] jsonToArray(String json,Class<T[]> classz) throws IOException {
        return new ObjectMapper().readValue(json, classz);

    }
}
