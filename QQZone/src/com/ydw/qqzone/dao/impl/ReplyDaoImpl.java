package com.ydw.qqzone.dao.impl;

import com.ydw.myssm.basedao.BaseDAO;
import com.ydw.qqzone.dao.ReplyDao;
import com.ydw.qqzone.pojo.Reply;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-15 0:34
 */
public class ReplyDaoImpl extends BaseDAO<Reply> implements ReplyDao {
    @Override
    public List<Reply> getReplyList(Integer topicId) {
        String sql = "SELECT id,content,replyDate,author,topic FROM t_reply \n" +
                "WHERE topic = ?";
        return executeQuery(sql,topicId);
    }

    @Override
    public void addReply(Reply reply) {

    }

    @Override
    public void deleteReply(Integer replyId) {

    }
}
