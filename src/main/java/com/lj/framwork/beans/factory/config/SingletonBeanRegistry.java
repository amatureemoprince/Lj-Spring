package com.lj.framwork.beans.factory.config;

/**
 * @ClassName SingletonBeanRegistry
 * @Description 单例模式工厂接口
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 9:15
 * @Version JDK 17
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

}
