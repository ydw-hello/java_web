package com.ydw.qqzone.dao;

import com.ydw.qqzone.pojo.Topic;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 17:28
 */
public interface TopicDao {
    // 获取指定用户的所有日志
    List<Topic> getTopicList(Integer uId);
    // 添加日志
    void addTopic(Topic topic);
    // 删除日志
    void deleteTopic(Integer topicId);
    // 获取特定日志信息
    Topic getTopic(Integer topicId);
}
