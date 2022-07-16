package com.ydw.qqzone.dao;

import com.ydw.qqzone.pojo.Reply;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 17:32
 */
public interface ReplyDao {
    // 根据特定日志查询回复列表
    List<Reply> getReplyList(Integer topicId);

    // 添加回复
    void addReply(Reply reply);
    // 删除回复
    void deleteReply(Integer replyId);
}
