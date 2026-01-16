package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.BeansException;
import com.lj.framwork.beans.factory.BeanFactory;
import com.lj.framwork.beans.factory.config.BeanDefinition;

/**
 * @ClassName AbstractBeanFactory
 * @Description 抽象 Bean 工厂基类。
 * 采用了“模板方法”设计模式，统一管理了 Bean 的获取流程：
 * 1. 尝试从单例缓存中获取。
 * 2. 如果缓存没有，则获取 Bean 定义并创建新的 Bean 实例。
 * 继承了 DefaultSingletonBeanRegistry，使其具备了单例注册和缓存的能力。
 * 实现了 BeanFactory 接口，对外提供标准的 getBean 方法。
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 9:10
 * @Version JDK 17
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 获取 Bean 实例的核心逻辑（模板方法）。
     * * @param beanName 需要检索的 Bean 名称
     * @return 返回完整的 Bean 实例
     * @throws BeansException 如果获取或创建失败抛出异常
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 1. 首先尝试从单例池（一级缓存）中获取已经创建好的单例
        Object bean = getSingleton(beanName);
        if (bean != null) {
            return bean;
        }

        // 2. 如果缓存中没有，则获取该 Bean 的“施工图纸”（BeanDefinition）
        // 该方法由子类实现，因为不同的子类可能有不同的存储方式（如 XML 或 Map）
        BeanDefinition beanDefinition = getBeanDefinition(beanName);

        // 3. 根据图纸创建新的 Bean 实例
        // 该方法同样由具体负责“生产”的子类来实现
        return createBean(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName, Object[] args) throws BeansException {
        // 1. 尝试从单例缓存中获取
        Object bean = getSingleton(beanName);

        // 2. 如果已经存在，直接返回（单例模式的体现）
        if (bean != null) {
            return bean;
        }

        // 3. 如果缓存没有，则获取该 Bean 的定义信息（图纸）
        BeanDefinition beanDefinition = getBeanDefinition(beanName);

        // 4. 调用 createBean 开启生命周期，并将 args 传进去用于实例化
        return createBean(beanName, beanDefinition, args);
    }

    /**
     * 获取指定名称的 Bean 定义信息。
     * 这是一个抽象方法，交由子类（如 DefaultListableBeanFactory）去实现具体的查找逻辑。
     * @param beanName getBeanDefinition
     * @return 对应的 BeanDefinition
     * @throws BeansException 找不到定义时抛出
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 根据 Bean 定义创建 Bean 实例。
     * 这是一个抽象方法，由负责实例化策略的子类（如 AbstractAutowireCapableBeanFactory）实现。
     * @param beanName createBean
     * @param beanDefinition Bean 定义信息
     * @return 创建好的 Bean 实例
     * @throws BeansException 创建失败时抛出
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

    /**
     * 创建 Bean 实例的抽象协议。
     * 由具体的自动化装配 Bean 工厂（如 AbstractAutowireCapableBeanFactory）负责实现。
     * @param beanName       createBean(...args)
     * @param beanDefinition Bean 的定义信息（包含 Class、属性等图纸信息）
     * @param args           显式指定的构造函数参数。如果是无参创建，该参数为 null。
     * @return 创建完成并初始化后的完整 Bean 对象
     * @throws BeansException 如果实例化、属性填充或初始化过程中发生错误
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

}
