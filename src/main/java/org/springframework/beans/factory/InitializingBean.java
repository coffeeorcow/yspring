package org.springframework.beans.factory;

public interface InitializingBean {

    void afterProperties() throws Exception;

}
