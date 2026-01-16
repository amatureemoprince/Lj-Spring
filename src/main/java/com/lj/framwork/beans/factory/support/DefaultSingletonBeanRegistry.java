package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.factory.config.SingletonBeanRegistry;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DefaultSingletonBeanRegistry
 * @Description 默认的单例 Bean 注册表实现类
 * 该类主要负责管理和存储在整个系统生命周期中只存在一份的 Bean 实例（单例）。
 * 它实现了 SingletonBeanRegistry 接口，提供了对单例对象的存取能力。
 * * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 9:14
 * @Version JDK 17
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 单例对象的缓存池（一级缓存）。
     * Key: Bean 的名称
     * Value: 已经实例化并初始化好的完整 Bean 对象
     * 这里为什么使用HashMap呢？
     * 单例模式实例的创建比较复杂，不是单一的ConcurrentHashMap能解决的，并且后续三级缓存的实现，使用ConcurrentHashMap更加容易死锁！
     */
    private final Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 获取指定名称的单例对象。
     * * @param beanName 需要检索的 Bean 名称
     * @return 返回缓存中的 Bean 实例，如果不存在则返回 null
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 将创建好的单例对象注册到缓存池中。
     * 该方法通常由具体的 BeanFactory 实现类在成功创建并初始化 Bean 后调用。
     * * @param beanName Bean 的唯一标识名称
     * @param singletonObject 完整的单例实例
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
