package org.springframework.test.ioc;

import org.junit.Test;
import org.springframework.beans.factory.PropertyValue;
import org.springframework.beans.factory.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.ioc.bean.Car;
import org.springframework.test.ioc.bean.Person;


public class PopulateBeanWithPropertyValuesTest {

    @Test
    public void testPopulateBeanWithPropertyValues() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册 Car 实例
        PropertyValues propertyValuesForCar = new PropertyValues();
        propertyValuesForCar.addProperty(new PropertyValue("brand", "porsche"));
        BeanDefinition carBeanDefinition = new BeanDefinition(Car.class, propertyValuesForCar);
        beanFactory.registerBeanDefinition("car", carBeanDefinition);

        // 注册 person 实例
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addProperty("name", "xyz");
        propertyValues.addProperty("age", 13);
        propertyValues.addProperty("car", new BeanReference("car"));
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
    }
}
