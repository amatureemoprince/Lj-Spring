package com.lj.framwork.beans.factory;

import java.util.Objects;

/**
 * @ClassName PropertyValue
 * @Description 表示 Bean 的一个属性信息（名称和值）
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/17 21:54
 * @Version JDK 17
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    /**
     * 重写 equals，方便在集合操作（如去重、对比）中使用
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PropertyValue that = (PropertyValue) o;
        return Objects.equals(name, that.name) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    /**
     * 重写 toString，方便在调试时直接看到属性注入的内容
     */
    @Override
    public String toString() {
        return "PropertyValue{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

}
