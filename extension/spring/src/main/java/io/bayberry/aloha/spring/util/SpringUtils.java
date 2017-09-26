package io.bayberry.aloha.spring.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Optional;

public class SpringUtils {

    public static <T> Optional<T> getBean(BeanFactory beanFactory, Class<T> beanClass) {
        try {
            return Optional.of(beanFactory.getBean(beanClass));
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.empty();
        }
    }

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
