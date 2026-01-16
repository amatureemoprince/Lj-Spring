package com.lj.framwork.beans.factory.config;

/**
 * @ClassName BeanDefinition
 * @Description Bean 的定义信息类
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/15 23:11
 * @Version JDK 17
 */
public class BeanDefinition {

    /**
     * Bean 的类对象（核心属性）。
     * 用于在运行时通过反射（Reflection）获取构造函数并实例化对象。也就是只定义模版，真正的实例化交给容器管理！
     */
    private Class beanClass;

    /**
     * 构造函数：创建一个新的 Bean 定义。
     * * @param beanClass 待实例化的 Bean 的类型
     */
    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 获取 Bean 的类型。
     * * @return 返回该 Bean 定义所指向的 Class 对象
     */
    public Class getBeanClass() {
        return this.beanClass;
    }

    /**
     * 设置 Bean 的类型。
     * * @param beanClass 新的 Class 对象
     */
    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
