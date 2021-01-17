package org.springframework.beans.factory;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addProperty(String name, Object value) {
        propertyValueList.add(new PropertyValue(name, value));
    }

    public void addProperty(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue p : propertyValueList) {
            if (p.getName().equals(propertyName)) {
                return p;
            }
        }

        return null;
    }

}
