package com.lj.framwork.test.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName UserDao
 * @Description
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/18 20:47
 * @Version JDK 17
 */
public class UserDao {

    private final static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "Tom");
        hashMap.put("10002", "Jenny");
        hashMap.put("10003", "Jack");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    };

}
