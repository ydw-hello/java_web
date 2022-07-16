package com.ydw.qqzone.dao;

import com.ydw.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 17:24
 */
public interface UserBasicDao {
    // 根据账号和密码获取特定用户信息
    UserBasic getUserBasic(String loginId,String pwd);

    // 根据id获取用户信息
    UserBasic getUserBasicById(Integer id);

    // 获取指定用户的好友信息
    List<UserBasic> getFriendList(Integer uId);



}
