package com.ydw.qqzone.service;

import com.ydw.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 20:23
 */
public interface UserBasicService {
    // 用户登录
    UserBasic login(String loginId,String pwd);

    // 获取好友列表
    List<UserBasic> getFriendList(UserBasic userBasic);

    // 根据用户id获取用户信息
    UserBasic getUserBasic(Integer id);
}
