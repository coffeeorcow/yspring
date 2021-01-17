package org.springframework.beans.factory.support;

import org.springframework.beans.factory.config.BeanDefinition;

/**
 * 注册 BeanDefinition
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
