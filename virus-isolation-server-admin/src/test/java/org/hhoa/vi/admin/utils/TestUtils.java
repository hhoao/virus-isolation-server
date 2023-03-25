package org.hhoa.vi.admin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * TestUtils
 *
 * @author hhoa
 * @since 2023/3/21
 **/

public class TestUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 调用ObjectMapper中的readValue方法，读取对象，主要是用于处理异常
     * @param str json字符串
     * @param type 需要转换的类型
     * @return E类型对象
     * @param <E> 转换和返回类型对象
     * @param <T> 继承自TypeReference,用于存储对象类型
     */
    public static <E, T extends TypeReference<E>> E jsonStringToObject(String str, T type){
        E obj;
        try {
            obj = objectMapper.readValue(str, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * 将对象转换为请求参数
     * @param object 对象
     * @return 请求参数
     */
    public static String ObjectToParametersString(Object object){
        if (object == null) {
            return "";
        }
        Map<String, String> parametersMap= objectMapper.convertValue(object, new TypeReference<Map<String, String>>() {});
        StringBuilder parameter = new StringBuilder();
        for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
            if (entry.getValue() != null) {
                parameter.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return parameter.deleteCharAt(parameter.length() - 1).toString();
    }
}
