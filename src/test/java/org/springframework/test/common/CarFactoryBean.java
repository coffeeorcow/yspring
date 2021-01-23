package org.springframework.test.common;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.test.bean.Car;

public class CarFactoryBean implements FactoryBean<Car> {

    private String brand;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrand(brand);
        System.out.println("FactoryBean generate Bean.");
        return car;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
