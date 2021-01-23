package org.springframework.aop;

import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.CglibAopProxy;
import org.springframework.aop.framework.JdkDynamicAopProxy;

public class ProxyFactory implements AopProxy {

    private AdvisedSupport advised;

    public ProxyFactory(AdvisedSupport advised) {
        this.advised = advised;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    protected AopProxy createAopProxy() {
        if (advised.isProxyTargetClass()) {
            return new CglibAopProxy(advised);
        }
        return new JdkDynamicAopProxy(advised);
    }

}
