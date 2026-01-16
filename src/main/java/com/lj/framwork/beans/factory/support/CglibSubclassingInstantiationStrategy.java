package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.BeansException;
import com.lj.framwork.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @ClassName CglibSubclassingInstantiationStrategy
 * @Description 基于 CGLIB 字节码生成的实例化策略。
 * 该策略不直接通过反射调用构造函数，而是利用 CGLIB 动态生成目标类的子类。
 * 优势：
 * 1. 它是 Spring 默认的实例化策略（在某些版本中）。
 * 2. 能够更好地支持方法拦截和动态代理。
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 10:53
 * @Version JDK 17
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    /**
     * 利用 CGLIB 增强器创建 Bean 实例。
     *
     * @param beanDefinition Bean 定义信息
     * @param beanName       Bean 名称
     * @param ctor           具体的构造函数
     * @param args           构造函数入参
     * @return 实例化后的 Bean 子类对象
     * @throws BeansException 实例化失败异常
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        // Enhancer 是 CGLIB 的核心类，用于动态生成子类
        Enhancer enhancer = new Enhancer();

        // 设置需要动态生成的子类的父类（即我们要实例化的那个 Bean 类）
        enhancer.setSuperclass(beanDefinition.getBeanClass());

        // 设置回调函数。
        // 因为这里我们只需要实例化，不需要做复杂的逻辑拦截，
        // 所以使用 NoOp.INSTANCE（No Operation），即不进行任何增强处理。
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        // 根据是否有指定构造函数，选择不同的创建方式
        if (null == ctor) {
            // 调用默认的无参构造逻辑
            return enhancer.create();
        }

        // 调用指定的含参构造逻辑
        // 参数 1：构造函数的参数类型数组；参数 2：具体的参数值数组
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
