package com.ydw.qqzone.service.impl;

import com.ydw.qqzone.dao.UserBasicDao;
import com.ydw.qqzone.pojo.UserBasic;
import com.ydw.qqzone.service.UserBasicService;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 20:24
 */
public class UserBasicServiceImpl implements UserBasicService {
    private UserBasicDao userBasicDao;
    @Override
    public UserBasic login(String loginId, String pwd) {
        return userBasicDao.getUserBasic(loginId, pwd);
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        return userBasicDao.getFriendList(userBasic.getId());
    }

    @Override
    public UserBasic getUserBasic(Integer id) {
        return userBasicDao.getUserBasicById(id);
    }
}
