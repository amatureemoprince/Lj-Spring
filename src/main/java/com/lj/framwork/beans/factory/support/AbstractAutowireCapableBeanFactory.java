package com.lj.framwork.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import com.lj.framwork.beans.BeansException;
import com.lj.framwork.beans.factory.PropertyValue;
import com.lj.framwork.beans.factory.PropertyValues;
import com.lj.framwork.beans.factory.config.BeanDefinition;
import com.lj.framwork.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * @Author Dark Chocolate 2069057986@qq.com
 * @ClassName AbstractAutowireCapableBeanFactory
 * @Description 具有自动装配能力的抽象 Bean 工厂。
 * 它是 Spring Bean 创建的核心实现者，负责了 Bean 的完整生命周期管理。
 * 核心流程包括：
 * 1. 实例化 (Instantiation)：根据 BeanDefinition 选定构造函数并创建对象。
 * 2. 属性填充 (Population)：识别普通属性值与 BeanReference 引用，并注入到对象中。
 * 3. 初始化 (Initialization)：调用初始化钩子方法（待扩展）。
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * 实例化策略接口。
     * 默认使用 Cglib 实现，支持动态生成子类，能更好地处理带有参数的构造函数。
     */
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    /**
     * 实现父类定义的无参创建逻辑。
     *
     * @param beanName       Bean 的名称
     * @param beanDefinition Bean 的定义信息
     * @return 完整的 Bean 实例
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        return createBean(beanName, beanDefinition, null);
    }

    /**
     * 核心创建逻辑：将实例化与属性填充有机结合。
     *
     * @param beanName       Bean 的名称
     * @param beanDefinition Bean 的定义信息
     * @param args           显式传入的构造参数（可为 null）
     * @return 创建好的 Bean 实例
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            // 阶段一：实例化
            // 利用指定的策略（JDK/CGLIB）创建出“原始对象”。
            bean = createBeanInstance(beanDefinition, beanName, args);

            // 阶段二：填充属性
            // 在对象有了“肉体”后，根据设计图（BeanDefinition）注入其依赖的“灵魂”（属性）。
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 阶段三：注册到单例缓存
        // 只有完整的、可用的 Bean 才会进入单例池。
        addSingleton(beanName, bean);

        return bean;
    }

    /**
     * 实例化逻辑：负责寻找合适的构造函数。
     * Spring 这里的逻辑非常复杂，本实现简化为通过参数数量匹配构造函数。
     *
     * @param beanDefinition Bean 定义
     * @param beanName       Bean 名称
     * @param args           构造参数
     * @return 初始对象实例
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();

        // 反射获取类中定义的所有构造函数
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

        // 简单的参数长度匹配逻辑。
        // 如果 args 不为空，则寻找参数个数一致的构造函数进行实例化。
        if (null != args) {
            for (Constructor<?> ctor : declaredConstructors) {
                if (ctor.getParameterTypes().length == args.length) {
                    constructorToUse = ctor;
                    break;
                }
            }
        }

        // 委托给实例化策略完成真正的对象创建。
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);

    }

    /**
     * 属性填充逻辑：这是实现 DI（依赖注入）的核心方法。
     * 它将 PropertyValues 中定义的静态数据转化为对象运行时的状态。
     *
     * @param beanName       Bean 名称
     * @param bean           已实例化的原始对象
     * @param beanDefinition Bean 定义元数据
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

                // 核心处理逻辑：处理 Bean 之间的依赖引用
                if (value instanceof BeanReference) {
                    // 如果值是引用类型，说明 A 依赖了 B。
                    // 此时触发 getBean(B) 逻辑。如果 B 尚未创建，这里会发生递归调用。
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }

                // 属性填充：利用 Hutool 的反射工具类。
                // 它会自动处理字段（Field）访问权限，并尝试调用对应的 Setter 方法。
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }
    }

    /**
     * 获取当前的实例化策略。
     */
    public InstantiationStrategy getInstantiationStrategy() {
        return this.instantiationStrategy;
    }

    /**
     * 设置实例化策略。
     *
     * @param instantiationStrategy 具体的实例化策略（如 Simple 或 Cglib）
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
