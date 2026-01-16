package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.BeansException;
import com.lj.framwork.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @Author Dark Chocolate 2069057986@qq.com
 * @ClassName AbstractAutowireCapableBeanFactory
 * @Description 具有自动装配能力的抽象 Bean 工厂。
 * 负责实现具体的 Bean 实例化、属性填充及初始化流程。
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 选择实例化策略，默认使用 Cglib 实现。
     * 也可以通过 setter 方法暴露出去，允许用户在外部切换为 SimpleInstantiationStrategy。
     */
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     * 实现父类定义的抽象创建方法。
     * 该方法是无参版本的实现，实际上是调用了带参数版本的 createBean。
     * * 在 Spring 的分层设计中，这样可以确保所有的 Bean 创建请求（无论是否带参）
     * 最终都会汇聚到同一个核心逻辑（带参数的 createBean）中。
     *
     * @param beanName       Bean 的名称
     * @param beanDefinition Bean 的定义信息
     * @return 创建好的完整 Bean 实例
     * @throws BeansException 如果实例化、属性填充或初始化过程中发生错误
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        // 直接透传 null 作为 args 参数。
        // 这将引导 createBeanInstance 走向“无参构造函数”的实例化路径。
        return createBean(beanName, beanDefinition, null);
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            // 1. 实例化阶段：交给专门的实例化方法处理
            bean = createBeanInstance(beanDefinition, beanName, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 2. 属性填充阶段（待实现）：后续我们会在这里调用 applyPropertyValues(beanName, bean, beanDefinition);

        // 3. 注册单例：将成品存入缓存池
        addSingleton(beanName, bean);

        return bean;
    }

    /**
     * 实例化逻辑：负责寻找合适的构造函数并调用策略类。
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();

        // 获取所有声明的构造函数
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

        // 简化的参数匹配逻辑：
        // 在实际 Spring 源码中，这里需要比对参数类型（Class<?>[]），而不仅仅是长度。
        // 目前我们先按数量匹配，这已经能处理大部分基础场景。
        if (null != args) {
            for (Constructor<?> ctor : declaredConstructors) {
                if (ctor.getParameterTypes().length == args.length) {
                    constructorToUse = ctor;
                    break;
                }
            }
        }

        // 调用具体的策略（JDK 或 CGLIB）进行实例化
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return this.instantiationStrategy;
    }


    /**
     * 设置实例化策略。
     * * 该方法允许在运行时或容器初始化时，动态替换 Bean 的创建算法。
     * 例如：
     * 1. 在生产环境追求极致性能且不需要代理时，可以切换为 {@link SimpleInstantiationStrategy}。
     * 2. 在需要支持方法注入或动态增强时，使用默认的 {@link CglibSubclassingInstantiationStrategy}。
     * @param instantiationStrategy 具体的实例化策略实现类
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
