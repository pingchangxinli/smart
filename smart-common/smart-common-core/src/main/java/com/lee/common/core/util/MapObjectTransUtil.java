package com.lee.common.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import lombok.NonNull;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author haitao.li
 */
public class MapObjectTransUtil {
    public  static Object toObject(@NonNull Map<String,Object> map, Class clazz) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Object object = clazz.newInstance();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.indexOf("_") > -1) {
                key = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
            }
            Field field = clazz.getField(key);
            if (ObjectUtils.isNotEmpty(field)) {
                field.set(object,value);
            }
        }
        return object;
    }
}
