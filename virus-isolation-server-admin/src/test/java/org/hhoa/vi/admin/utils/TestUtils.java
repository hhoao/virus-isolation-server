package org.hhoa.vi.portal.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
