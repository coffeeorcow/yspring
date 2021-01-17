package org.springframework.beans.factory.support;

import org.springframework.beans.BeanUtil;
import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        return doCreateBean(beanName, beanDefinition);
    }

    private Object doCreateBean(String beanName, BeanDefinition beanDefinition) {
        Object bean;
        try {
            bean = createBeanInstance(beanDefinition);

            // 为 bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("bean 创建异常");
        }

        addSingleton(beanName, bean);
        return bean;
    }

    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue property : beanDefinition.getPropertyValues().getPropertyValues())  {
                String name = property.getName();
                Object value = property.getValue();
                BeanUtil.setFieldsValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName);
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition) {
        InstantiationStrategy instantiationStrategy = getInstantiationStrategy();
        return instantiationStrategy.instantiate(beanDefinition);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
