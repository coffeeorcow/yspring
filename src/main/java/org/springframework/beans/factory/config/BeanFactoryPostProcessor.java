package org.springframework.beans.factory.config;

import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * 自定义修改 BeanDefinition
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有BeanDefintion加载完成后，但在bean实例化之前，提供修改BeanDefinition属性值的机制
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
