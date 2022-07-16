package com.ydw.qqzone.service.impl;

import com.ydw.myssm.basedao.BaseDAO;
import com.ydw.qqzone.dao.TopicDao;
import com.ydw.qqzone.pojo.Topic;
import com.ydw.qqzone.service.TopicService;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 20:35
 */
public class TopicServiceImpl extends BaseDAO<Topic> implements TopicService {
    private TopicDao topicDao;
    @Override
    public List<Topic> getTopicList(Integer uId) {
        String sql = "SELECT id,title,content,topicDate,author FROM `t_topic` WHERE author=?";
        return executeQuery(sql,uId);
    }

    @Override
    public Topic getTopicDetail(Integer topicId) {
        String sql = "SELECT id,title,content,topicDate,author FROM t_topic WHERE id=?";
        return load(sql,topicId);
    }
}
