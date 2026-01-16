package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.factory.config.BeanDefinition;

/**
 * @ClassName BeanDefinitionRegistry
 * @Description Bean 定义注册表接口。
 * 该接口定义了关于 BeanDefinition 的核心管理操作，包括注册、移除、查询等。
 * 它是连接“资源加载（如 XML/注解解析）”与“容器内部存储”之间的桥梁。
 * 在 Spring 体系中，通常由具体的 BeanFactory 实现该接口，
 * 使得工厂既能作为生产 Bean 的场所，也能作为存储 Bean 蓝图的仓库。
 *
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 9:14
 * @Version JDK 17
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册一个新的 Bean 定义。
     * @param beanName 需要注册的 Bean 实例名称，作为唯一索引
     * @param beanDefinition 包含 Bean 实例化信息的定义对象（蓝图）
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
