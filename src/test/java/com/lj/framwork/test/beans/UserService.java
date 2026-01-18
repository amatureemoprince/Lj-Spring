package com.lj.framwork.test.beans;

/**
 * @ClassName UserService
 * @Description
 * @Author Dark Chocolate 2069057986@qq.com
 * @Date 2026/1/18 20:53
 * @Version JDK 17
 */
public class UserService {

    private String uId;

    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("query the user info: " + userDao.queryUserName(uId));
    }

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public void setuId(String uId){
        this.uId = uId;
    }

    public UserDao getUserDao(){
        return this.userDao;
    }

    public String getuId(){
        return this.uId;
    }

}
