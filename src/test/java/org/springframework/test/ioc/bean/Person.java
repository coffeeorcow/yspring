package org.springframework.test.ioc.bean;

import lombok.Data;

@Data
public class Person {

    private String name;
    private int age;
    private Car car;

}
