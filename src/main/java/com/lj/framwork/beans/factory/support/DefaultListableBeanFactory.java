package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.BeansException;
import com.lj.framwork.beans.factory.config.BeanDefinition;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DefaultListableBeanFactory
 * @Description 核心 Bean 工厂实现类。
 * 该类是 Spring 容器引擎的重中之重，它集成了以下能力：
 * 1. 继承 AbstractAutowireCapableBeanFactory：拥有了实例化、自动装配 Bean 的能力。
 * 2. 实现了 BeanDefinitionRegistry：拥有了注册、管理 Bean 定义（图纸）的能力。
 * * 在实际运行中，它是作为存储 Bean 定义的核心仓库，也是用户直接操作的工厂实现。
 * * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 9:14
 * @Version JDK 17
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    /**
     * 存储 Bean 定义的“图纸柜”。
     * 这里使用 ConcurrentHashMap 以支持在多线程环境下（如并发扫描包）安全地注册 Bean 定义。
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 向注册表中注册一个 Bean 定义。
     * @param beanName Bean 的唯一标识名称
     * @param beanDefinition Bean 的定义信息（图纸）
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 从注册表中获取指定的 Bean 定义。
     * @param beanName Bean 的名称
     * @return 对应的 BeanDefinition 实例
     * @throws BeansException 如果找不到对应的图纸，则抛出异常
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            // 抛出带有上下文信息的异常，方便开发者定位是哪个 Bean 没定义
            throw new BeansException("No bean named '" + beanName + "' is defined");
        }

        return beanDefinition;
    }

}
