package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        return doCreateBean(beanName, beanDefinition);
    }

    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        Object bean;
        try {
            bean = beanClass.newInstance();
        } catch (Exception e) {
            throw new BeansException("bean 创建异常");
        }

        addSingleton(beanName, bean);
        return bean;
    }
}
