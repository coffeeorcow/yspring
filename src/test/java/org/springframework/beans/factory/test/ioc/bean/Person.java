package org.springframework.beans.factory.test.ioc.bean;

public class Person {

    private String name;
    private Integer age;
    private Car car;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", car=" + car +
                '}';
    }
}
