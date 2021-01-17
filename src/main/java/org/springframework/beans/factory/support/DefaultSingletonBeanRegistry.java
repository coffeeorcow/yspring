package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonBeanMap.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonBean) {
        singletonBeanMap.put(beanName, singletonBean);
    }

}
