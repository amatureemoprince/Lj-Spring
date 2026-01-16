package com.lj.framwork.beans.factory.support;

import com.lj.framwork.beans.BeansException;
import com.lj.framwork.beans.factory.config.BeanDefinition;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName SimpleInstantiationStrategy
 * @Description 简单的 Bean 实例化策略实现类。
 * 该类通过 JDK 自带的反射机制（Reflection API）来创建 Bean 实例。
 * 适用场景：
 * 1. 存在无参构造函数时。
 * 2. 存在指定的含参构造函数，且能够明确匹配参数类型时。
 * 注意：该策略不处理 CGLIB 动态代理等高级字节码增强逻辑。
 *
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 10:52
 * @Version JDK 17
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    /**
     * 利用反射创建 Bean 实例。
     * @param beanDefinition Bean 定义信息
     * @param beanName       Bean 名称
     * @param ctor           具体的构造函数。如果不为空，则优先使用该构造函数。
     * @param args           构造函数入参。
     * @return 实例化后的 Object
     * @throws BeansException 包装并抛出实例化过程中的各类异常
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        // 获取 Class 对象
        Class<?> clazz = beanDefinition.getBeanClass();
        try {
            if (null != ctor) {
                // 情况 A：已经明确了要用哪个构造函数（通常用于含参构造）
                // ctor.getParameterTypes() 用于二次确认构造函数的参数指纹
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                // 情况 B：没有指定构造函数，默认调用无参构造函数
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // 将 JDK 细碎的反射异常统一包装为框架自定义的 BeansException
            // 这样做是为了对调用者隐藏底层技术实现（反射），只传达业务层面的失败原因
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);
        }
    }
}
