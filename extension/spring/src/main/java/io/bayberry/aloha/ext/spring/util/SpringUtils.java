package io.bayberry.aloha.ext.spring.util;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class SpringUtils {

    public static <T> T getOrRegisterBean(ConfigurableListableBeanFactory beanFactory, T bean) {
        try {
            return beanFactory.getBean((Class<T>) bean.getClass());
        } catch (NoSuchBeanDefinitionException exception) {
            beanFactory
                .registerSingleton(bean.getClass().getCanonicalName(), bean);
            return bean;
        }
    }
}
