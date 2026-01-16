package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.BeansException;
import com.lj.framwork.beans.factory.config.BeanDefinition;

/**
 * @ClassName AbstractAutowireCapableBeanFactory
 * @Description 具有自动装配能力的抽象 Bean 工厂。
 * 它是 AbstractBeanFactory 的进一步扩展，实现了最核心的 Bean 创建逻辑。
 * 该类主要关注：如何通过反射实例化对象、如何进行属性填充以及如何执行初始化方法。
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 9:58
 * @Version JDK 17
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 实现父类定义的抽象方法，具体负责 Bean 的创建。
     * @param beanName Bean 的名称
     * @param beanDefinition Bean 的定义信息（包含类信息、属性等）
     * @return 创建好的完整 Bean 实例
     * @throws BeansException 如果实例化失败抛出异常
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            // 核心步骤 1：实例化 Bean
            // 通过 BeanDefinition 中保存的 Class 对象，利用反射调用其默认无参构造函数
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // 如果类没有无参构造函数或访问受限，则抛出自定义异常并包装原始错误原因
            throw new BeansException("Instantiation of bean failed !", e);
        }

        // 核心步骤 2：将创建好的单例对象存入单例池中
        // 这样下次 getBean 时就能直接从 DefaultSingletonBeanRegistry 的缓存中获取
        addSingleton(beanName, bean);

        return bean;
    }

}
