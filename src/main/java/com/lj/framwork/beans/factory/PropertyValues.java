package com.lj.framwork.beans.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PropertyValues
 * @Description 属性值集合容器。
 * 用于包装和管理一个 Bean 定义中所有的 {@link PropertyValue}。
 * 在 Bean 的生命周期中，该容器承载了从配置文件（如 XML）中解析出的属性信息，
 * 并在属性填充阶段（populateBean）被转化为真正的对象属性。
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/17 21:56
 * @Version JDK 17
 */
public class PropertyValues {

    /**
     * 存放所有属性值的列表
     */
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    /**
     * 向集合中添加一个属性值对象
     * @param pv 属性值对象
     */
    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    /**
     * 获取所有的属性值对象，以数组形式返回
     * 这样做可以防止外部直接修改内部的 List，并方便进行遍历操作
     * @return PropertyValue 数组
     */
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    /**
     * 根据属性名称获取对应的属性值对象
     * @param propertyName 属性名称
     * @return 对应的 PropertyValue 对象；如果不存在则返回 null
     */
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue pv : this.propertyValueList) {
            if (pv.getName().equals(propertyName)) {
                return pv;
            }
        }
        return null;
    }
}
