package org.springframework.beans.factory.test.ioc.service;

public class HelloService {
    public String sayHello() {
        System.out.println("hello");
        return "hello";
    }
}
