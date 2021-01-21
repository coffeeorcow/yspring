package org.springframework.beans.factory.config;

import org.springframework.beans.factory.BeansException;

/**
 * 实例化 bean 后的拓展
 */
public interface BeanPostProcessor {

    /**
     * 初始化 bean 之前的操作
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 初始化 bean 之后的操作
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
