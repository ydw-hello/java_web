package com.ydw.qqzone.service;

import com.ydw.qqzone.pojo.Topic;
import com.ydw.qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 20:34
 */
public interface TopicService {
    // 查询特定用户的日志列表信息
    List<Topic> getTopicList(Integer uId);

    // 根据日志id获取日志信息
    Topic getTopicDetail(Integer topicId);
}
