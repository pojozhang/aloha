package io.bayberry.aloha.spring.util;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class SpringUtils {

    public static <T> T getOrRegisterBean(ConfigurableListableBeanFactory beanFactory, T bean) {
        return getOrRegisterBean(beanFactory, bean, bean.getClass().getCanonicalName());
    }

    public static <T> T getOrRegisterBean(ConfigurableListableBeanFactory beanFactory, T bean, String beanName) {
        try {
            return beanFactory.getBean(beanName, (Class<T>) bean.getClass());
        } catch (NoSuchBeanDefinitionException exception) {
            beanFactory
                    .registerSingleton(beanName, bean);
            return bean;
        }
    }
}
