package com.lj.framwork.beans.factory.config;

/**
 * @ClassName BeanReference
 * @Description Bean 引用类。
 * 用于在 BeanDefinition 的 PropertyValues 中标记对另一个 Bean 的依赖。
 * 当一个 Bean 的属性依赖于容器中的另一个 Bean 时（例如 UserService 依赖 UserDao），
 * 我们在解析配置阶段还拿不到真实的依赖对象实例，此时会先创建一个 BeanReference，
 * 并存储目标 Bean 的名称。在后续的属性填充（populateBean）阶段，
 * 容器会根据这个引用去执行 getBean 操作，从而完成递归注入。
 *
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/17 21:53
 * @Version JDK 17
 */
public class BeanReference {

    /**
     * 目标依赖 Bean 的名称
     */
    private String beanName;

    /**
     * 构造函数：创建一个对特定名称 Bean 的引用
     * @param beanName 目标 Bean 的名称
     */
    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    /**
     * 获取引用的 Bean 名称
     * @return Bean 的标识符
     */
    public String getBeanName() {
        return this.beanName;
    }

    /**
     * 设置引用的 Bean 名称
     * @param beanName Bean 的标识符
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
