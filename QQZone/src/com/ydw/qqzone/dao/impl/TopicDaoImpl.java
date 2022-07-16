package com.ydw.qqzone.dao.impl;

import com.ydw.myssm.basedao.BaseDAO;
import com.ydw.qqzone.dao.TopicDao;
import com.ydw.qqzone.pojo.Topic;

import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 20:09
 */
public class TopicDaoImpl extends BaseDAO<Topic> implements TopicDao {
    @Override
    public List<Topic> getTopicList(Integer uId) {
        String sql = "select id, title, content, topicDate, author\n" +
                "from t_topic\n" +
                "where author = ?";
        return executeQuery(sql,uId);
    }

    @Override
    public void addTopic(Topic topic) {
        String sql  = "insert into t_topic(title, content, topicDate, author) values(?,?,?,?) ";
        executeUpdate(sql,topic.getTitle(),topic.getContent(),topic.getTopicDate(),topic.getAuthor().getId());
    }

    @Override
    public void deleteTopic(Integer topicId) {
        String sql = "delete from t_topic where id=?";
        executeUpdate(sql,topicId);
    }

    @Override
    public Topic getTopic(Integer topicId) {
        String sql = "select id, title, content, topicDate, author\n" +
                "from t_topic\n" +
                "where id = ?";
        return load(sql,topicId);
    }
}
