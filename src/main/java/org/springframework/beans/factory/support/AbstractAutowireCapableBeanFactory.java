package org.springframework.beans.factory.support;

import org.springframework.beans.BeanUtil;
import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.PropertyValue;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.BeanReference;

import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

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

            // 调用 bean 的初始化方法
            initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("bean 创建异常");
        }

        addSingleton(beanName, bean);
        return bean;
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // bean 前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 调用 bean 的初始化方法
        invokeInitMethods(beanName, bean, beanDefinition);

        // bean 后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue property : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = property.getName();
                Object value = property.getValue();

                if (value instanceof BeanReference) {
                    BeanReference ref = (BeanReference) value;
                    value = getBean(ref.getBeanName());
                }

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

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) {
        System.out.println("执行bean[" + beanName + "]的初始化方法");
    }

}
