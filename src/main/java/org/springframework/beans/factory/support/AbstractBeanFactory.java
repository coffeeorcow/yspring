package org.springframework.beans.factory.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private final Map<String, Object> factoryBeanObjectCache = new HashMap<>();

    @Override
    public Object getBean(String beanName) {
        Object sharedInstance = getSingleton(beanName);
        if (sharedInstance != null) {
            return getObjectForBeanInstance(sharedInstance, beanName);
        }

        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object obj = beanInstance;
        if (obj instanceof FactoryBean) {
            FactoryBean factoryBean = (FactoryBean) beanInstance;
            try {
                if (factoryBean.isSingleton()) {
                    //singleton作用域bean，从缓存中获取
                    obj = this.factoryBeanObjectCache.get(beanName);
                    if (obj == null) {
                        obj = factoryBean.getObject();
                        this.factoryBeanObjectCache.put(beanName, obj);
                    }
                } else {
                    //prototype作用域bean，新创建bean
                    obj = factoryBean.getObject();
                }
            } catch (Exception ex) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", ex);
            }
        }
        return obj;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition);

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    public void addBeanPostProcessor(BeanPostProcessor processor) {
        beanPostProcessors.remove(processor);
        beanPostProcessors.add(processor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

}
