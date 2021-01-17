package org.springframework.beans.factory.test.ioc.bean;

public class Car {

    private String brand;

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
