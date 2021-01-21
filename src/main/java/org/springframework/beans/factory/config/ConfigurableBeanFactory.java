package org.springframework.beans.factory.config;

import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.HierarchicalBeanFactory;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName
     * @return
     * @throws BeansException 如果找不到BeanDefintion
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例实例
     *
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
