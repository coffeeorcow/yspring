package org.springframework.aop.aspectj;

import org.springframework.aop.BeforeAdvice;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Method method, Object[] args, Object target) throws Throwable;

}
