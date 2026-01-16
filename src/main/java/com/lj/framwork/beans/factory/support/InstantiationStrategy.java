package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.BeansException;
import com.lj.framwork.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @ClassName InstantiationStrategy
 * @Description Bean 实例化策略接口。
 * 该接口定义了创建 Bean 实例的基本契约。通过引入该接口，可以将“如何创建对象”的逻辑
 * 从 BeanFactory 核心流程中解耦出来。
 * 常见的实现方案包括：
 * 1. SimpleInstantiationStrategy：利用 JDK 的反射机制（Constructor.newInstance）进行实例化。
 * 2. CglibSubclassingInstantiationStrategy：利用 CGLIB 动态生成子类的方式进行实例化（常用于处理 Method Injection 等高级场景）。
 *
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 10:52
 * @Version JDK 17
 */
public interface InstantiationStrategy {

    /**
     * 根据指定的策略实例化对象。
     *
     * @param beanDefinition Bean 的定义信息，包含 beanClass 等重要元数据
     * @param beanName       Bean 的名称，用于在发生异常时提供错误上下文
     * @param ctor           指定的构造函数。如果该参数不为空，则应使用该构造函数进行实例化；
     * 如果为空，则尝试寻找默认的无参构造函数。
     * @param args           传递给构造函数的参数数组。
     * @return 实例化后的 Bean 对象（通常还是个空壳，尚未填充属性）
     * @throws BeansException 如果实例化过程中发生任何错误（如找不到匹配的构造函数、访问受限等）
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}
