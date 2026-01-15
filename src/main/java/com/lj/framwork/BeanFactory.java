package com.lj.framwork;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName BeanFactory
 * @Description Bean工厂，用于将Bean定义到Map中，并提供Bean
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/15 23:11
 * @Version JDK 17
 */
public class BeanFactory {

    // bean的名称为‘键’，BeanDefinition为‘值’
    // ConcurrentHashMap: 适合高并发场景，细颗粒度，读不锁
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

}
