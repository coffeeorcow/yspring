package org.springframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.MethodBeforeAdvice;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice beforeAdvice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        beforeAdvice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
