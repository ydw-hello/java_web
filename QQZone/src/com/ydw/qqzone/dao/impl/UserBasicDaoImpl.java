package com.ydw.qqzone.dao.impl;

import com.ydw.myssm.basedao.BaseDAO;
import com.ydw.myssm.basedao.ConnUtil;
import com.ydw.qqzone.dao.UserBasicDao;
import com.ydw.qqzone.pojo.UserBasic;

import java.sql.Connection;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 17:40
 */
public class UserBasicDaoImpl extends BaseDAO<UserBasic> implements UserBasicDao {
    @Override
    public UserBasic getUserBasic(String loginId, String pwd) {
        String sql = "select id,loginId,nickName,pwd,headImg from t_user_basic where loginId=? and pwd=?";
        UserBasic userBasic = load(sql, loginId, pwd);
        return userBasic;
    }

    @Override
    public UserBasic getUserBasicById(Integer id) {
        return load("select id,loginId,nickName,pwd,headImg from t_user_basic where id=?",id);
    }

    @Override
    public List<UserBasic> getFriendList(Integer uId) {
        String sql = "select id,loginId,nickName,pwd,headImg\n" +
                "from t_user_basic tub\n" +
                "where id in (select fid id from t_friend where uid=?)";
        List<UserBasic> userBasics = executeQuery(sql, uId);
        return userBasics;
    }
}
