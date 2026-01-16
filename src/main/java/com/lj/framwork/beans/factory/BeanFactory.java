package com.lj.framwork.beans.factory;

import com.lj.framwork.beans.BeansException;

/**
 * @ClassName BeanFactory
 * @Description Bean工厂，用于将Bean定义到Map中，并提供Bean
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 9:11
 * @Version JDK 17
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

}
