package com.ydw.qqzone.dao.impl;

import com.ydw.myssm.basedao.BaseDAO;
import com.ydw.qqzone.dao.HostReplyDao;
import com.ydw.qqzone.pojo.HostReply;

/**
 * @author ydw
 * @create 2022-07-15 0:45
 */
public class HostReplyDaoImpl extends BaseDAO<HostReply> implements HostReplyDao {
    @Override
    public HostReply getHostReply(Integer replyId) {
        String sql = "SELECT id,content,hostReplyDate,author FROM `t_host_reply` WHERE reply= ?";
        return load(sql,replyId);
    }

    @Override
    public void addHostReply(HostReply hostReply) {

    }

    @Override
    public void deleteHostReply(Integer hostReplyId) {

    }
}
