package com.lj.framwork.beans;

/**
 * @ClassName BeansException
 * @Description Bean 异常基类。
 * 用于定义在 Bean 的定义、实例化、获取以及依赖注入过程中可能发生的各种异常。
 * 继承自 RuntimeException（运行时异常）通常比 Exception 更好，
 * 因为这可以让代码更简洁，不需要在每一层方法调用上都显式声明 throws。
 *
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/16 9:13
 * @Version JDK 17
 */
public class BeansException extends RuntimeException {

    /**
     * 基础错误消息构造函数
     * @param msg 具体的错误描述信息
     */
    public BeansException(String msg) {
        super(msg);
    }

    /**
     * 带原因的错误消息构造函数（用于异常转译/包装）
     * @param msg 具体的错误描述信息
     * @param cause 原始的异常原因（例如反射产生的 NoSuchMethodException 等）
     */
    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
