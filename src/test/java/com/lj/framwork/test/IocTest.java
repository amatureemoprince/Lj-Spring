package com.lj.framwork.test;

import com.lj.framwork.beans.factory.PropertyValue;
import com.lj.framwork.beans.factory.PropertyValues;
import com.lj.framwork.beans.factory.config.BeanDefinition;
import com.lj.framwork.beans.factory.config.BeanReference;
import com.lj.framwork.beans.factory.support.DefaultListableBeanFactory;
import com.lj.framwork.test.beans.UserDao;
import com.lj.framwork.test.beans.UserService;
import org.junit.jupiter.api.Test;

/**
 * @ClassName IocTest
 * @Description 测试 IoC 容器的 Bean 注册、实例化以及属性填充（包括引用注入）功能
 * @Author Dark Chocolate
 * @Date 2026/1/18 20:46
 * @Version JDK 17
 */
public class IocTest {

    @Test
    public void InsertIntoBean(){
        // 1. 初始化 BeanFactory：这是 IoC 的核心容器，用于持有 BeanDefinition 和实例化后的单例对象
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 注册 UserDao：
        // 这里将 UserDao 的 Class 信息封装进 BeanDefinition。
        // 此时并没有创建对象，只是把“图纸”存入 beanFactory 的 Map 中。
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. 构建 UserService 的“配置信息”：
        // PropertyValues 就像是一个篮子，装载了该 Bean 在初始化时需要填入的所有属性。
        PropertyValues propertyValues = new PropertyValues();

        // 注入普通属性：直接将字符串 "10001" 赋给名为 "uId" 的成员变量
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));

        // 注入对象引用：关键点！BeanReference 告诉容器：“这里不要填入字符串，请去容器里找名为 userDao 的对象填进来”
        // 这种设计解耦了 Bean 之间的硬编码依赖，是实现 DI（依赖注入）的精髓。
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4. 注册 UserService 的 BeanDefinition：
        // 这一步将 UserService 类信息及其对应的属性集合（propertyValues）一起绑定，存入容器。
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. 核心触发点：获取 UserService 实例
        // 此时容器会执行：
        // a. 实例化：调用你之前写的那个 CGLIB 实例化代码。
        // b. 属性填充：遍历 PropertyValues，发现有 BeanReference 时，递归调用 getBean("userDao")。
        UserService userService = (UserService) beanFactory.getBean("userService");

        // 6. 调用业务方法：如果控制台成功打印出 uId 且没有报 NullPointerException，
        // 说明 UserService 及其内部依赖的 UserDao 都已成功初始化并关联。
        userService.queryUserInfo();
    }
}
