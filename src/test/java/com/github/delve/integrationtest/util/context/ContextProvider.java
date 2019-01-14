package com.github.delve.integrationtest.util.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextProvider implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static <T> T getBean(final Class<T> beanClass) {
        return CONTEXT.getBean(beanClass);
    }

    public static Object getBean(final String beanName) {
        return CONTEXT.getBean(beanName);
    }
}
