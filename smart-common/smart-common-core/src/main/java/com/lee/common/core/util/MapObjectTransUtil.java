package com.lee.common.core.util;

import com.google.common.base.CaseFormat;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author lee.li
 */
public class MapObjectTransUtil<T> {
    /**
     * 将map转换成object引用,map中key用 下划线标识
     * 暂时代码中固定排除access_token参数传入
     * 如果map未传入任何object参数,将返回null
     * @param map   需转换
     * @param clazz 转换成的引用的class
     * @param <T>   class
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    public static <T> T toObject(@NonNull Map<String, Object> map, Class clazz) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        T object = (T) clazz.newInstance();
        boolean flag = false;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if ("access_token".equals(key) || "page".equals(key) || "limit".equals(key)) {
                continue;
            }
            if (key.indexOf("_") > -1) {
                key = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
            }
            Field field = clazz.getField(key);
            if (ObjectUtils.isNotEmpty(field)) {
                field.set(object, value);
                flag = true;
            }
        }
        if (!flag) {
            return null;
        }
        return object;
    }
}
