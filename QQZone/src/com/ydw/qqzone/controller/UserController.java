package com.ydw.qqzone.controller;

import com.ydw.qqzone.pojo.Topic;
import com.ydw.qqzone.pojo.UserBasic;
import com.ydw.qqzone.service.TopicService;
import com.ydw.qqzone.service.UserBasicService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ydw
 * @create 2022-07-14 20:41
 */
public class UserController {
    private UserBasicService userBasicService;
    private TopicService topicService;

    // 登录
    public String login(String loginId, String pwd, HttpSession session) {
        UserBasic userBasic = userBasicService.login(loginId, pwd);
        if (userBasic != null) {
            // 1.验证成功
            // 2.准备首页数据
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            List<Topic> topicList = topicService.getTopicList(userBasic.getId());

            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);

            // 用来保存自己的信息
            session.setAttribute("userBasic",userBasic);
            // 用来保存日志信息(当进入不同的空间时，存入不同的内容)
            session.setAttribute("friend",userBasic);
            return "index";
        }else{
            // 验证失败,返回首页
            return "login";
        }
    }

    // 进入好友的空间
    public String friend(Integer id, HttpSession session){
        // 1.根据id获取好友的信息
        UserBasic userBasic = userBasicService.getUserBasic(id);
        // 2.获取日志信息
        List<Topic> topicList = topicService.getTopicList(id);

        // 将页面的日志信息更新
        userBasic.setTopicList(topicList);
        session.setAttribute("friend",userBasic);
        return "index";
    }
}
