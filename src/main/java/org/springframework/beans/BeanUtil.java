package org.springframework.beans;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class BeanUtil {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void setFieldsValue(Object bean, String fieldName, Object value) {
        if (value == null) {
            return;
        }
        if (bean instanceof Map) {
            ((Map) bean).put(fieldName, value);
        } else if (bean instanceof Collection) {
            ((Collection) bean).add(value);
        } else {
            try {
                Field field = bean.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(bean, value);
            } catch (Exception e) {
                throw new UtilException("为 bean 设置属性值失败 fieldName => " + fieldName
                        + " value => " + value + " msg => " + e.getMessage());
            }
        }
    }


}
