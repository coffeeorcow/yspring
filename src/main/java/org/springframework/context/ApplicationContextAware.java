package org.springframework.context;

import org.springframework.beans.factory.BeansException;

public interface ApplicationContextAware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
