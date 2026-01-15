package com.lj.framwork;

/**
 * @ClassName BeanDefinition
 * @Description 实例化Bean的信息
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/15 23:11
 * @Version JDK 17
 */
public class BeanDefinition {

    private Object bean;

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Object getBean(){
        return this.bean;
    }

}
