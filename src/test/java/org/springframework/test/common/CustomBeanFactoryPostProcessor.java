package org.springframework.test.common;

import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition personBeanDefiniton = beanFactory.getBeanDefinition("person");
        PropertyValues propertyValues = personBeanDefiniton.getPropertyValues();
        //将person的name属性改为ivy
        propertyValues.addProperty("name", "ivy");
    }

}
