package com.lj.framwork.beans.factory.config;

import com.lj.framwork.beans.factory.PropertyValues;

/**
 * @ClassName BeanDefinition
 * @Description Bean 的定义信息类。
 * 它是 Spring 框架中非常核心的元数据载体，包含了 Bean 的类型信息（用于实例化）
 * 以及 Bean 的属性配置信息（用于属性填充）。
 * 容器会根据这张“图纸”来完成 Bean 的完整生命周期管理。
 *
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/15 23:11
 * @Version JDK 17
 */
public class BeanDefinition {

    /**
     * Bean 的类对象（核心属性）。
     * 用于在运行时通过反射（Reflection）获取构造函数并实例化对象。
     * 容器通过这个 Class 模板来创建具体的 Object 实例。
     */
    private Class beanClass;

    /**
     * Bean 的属性集合。
     * 包含了该 Bean 在配置文件中定义的各种属性名称和值。
     * 在对象实例化之后，容器会遍历该集合并利用反射将值注入到实例中。
     */
    private PropertyValues propertyValues;

    /**
     * 构造函数：仅指定 Bean 类型。
     * 默认会初始化一个空的 PropertyValues，防止后续操作出现空指针异常。
     *
     * @param beanClass 待实例化的 Bean 的类型
     */
    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    /**
     * 构造函数：完整指定 Bean 类型和属性集合。
     *
     * @param beanClass      待实例化的 Bean 的类型
     * @param propertyValues 预定义的属性集合
     */
    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        // 如果外部传入 null，则创建一个空的集合确保安全性
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    /**
     * 获取 Bean 的类型。
     *
     * @return 返回该 Bean 定义所指向的 Class 对象
     */
    public Class getBeanClass() {
        return this.beanClass;
    }

    /**
     * 设置 Bean 的类型。
     *
     * @param beanClass 新的 Class 对象
     */
    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 获取该 Bean 定义中所有的属性值。
     *
     * @return PropertyValues 属性值集合对象
     */
    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    /**
     * 为该 Bean 定义设置属性值集合。
     *
     * @param propertyValues 属性值集合对象
     */
    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
