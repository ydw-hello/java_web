package com.ydw.qqzone.service;

import com.ydw.qqzone.pojo.Reply;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-15 0:30
 */
public interface ReplyService {
    // 获取特定topic对应的reply信息
    List<Reply> getReplyList(Integer topicId);
}
