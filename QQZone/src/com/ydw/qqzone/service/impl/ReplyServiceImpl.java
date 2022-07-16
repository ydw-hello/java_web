package com.ydw.qqzone.service.impl;

import com.ydw.qqzone.dao.ReplyDao;
import com.ydw.qqzone.pojo.Reply;
import com.ydw.qqzone.service.ReplyService;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-15 0:31
 */
public class ReplyServiceImpl implements ReplyService {
    private ReplyDao replyDao;

    @Override
    public List<Reply> getReplyList(Integer topicId) {
        return replyDao.getReplyList(topicId);
    }
}
