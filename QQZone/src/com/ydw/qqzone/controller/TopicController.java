package com.ydw.qqzone.controller;

import com.ydw.qqzone.pojo.HostReply;
import com.ydw.qqzone.pojo.Reply;
import com.ydw.qqzone.pojo.Topic;
import com.ydw.qqzone.service.HostReplyService;
import com.ydw.qqzone.service.ReplyService;
import com.ydw.qqzone.service.TopicService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-15 0:23
 */
public class TopicController {
    private TopicService topicService;
    private ReplyService replyService;
    private HostReplyService hostReplyService;
    // 获取日志详情
    public String topicDetail(Integer id, HttpSession session){
        // 1.查询topic的信息
        Topic topicDetail = topicService.getTopicDetail(id);
        // 2.查询topic对应的回复信息
        List<Reply> replyList = replyService.getReplyList(id);
        List<Reply> replies = new ArrayList<>(replyList.size());
        // 3.查询topic中回复对应的主人回复信息
        for (int i=0;i<replyList.size();i++) {
            Reply reply = replyList.get(i);
            HostReply hostReply = hostReplyService.getHostReply(reply.getId());
            if(hostReply!=null){
                reply.setHostReply(hostReply);
            }
            replies.add(i,reply);
        }
        topicDetail.setReplyList(replies);

        session.setAttribute("topicDetail",topicDetail);

        return "frames/detail";
    }
}
