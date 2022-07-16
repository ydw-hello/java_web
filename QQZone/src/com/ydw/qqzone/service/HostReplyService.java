package com.ydw.qqzone.service;

import com.ydw.qqzone.pojo.HostReply;

/**
 * @author ydw
 * @create 2022-07-15 0:41
 */
public interface HostReplyService {
    // 根据reply的id获取HostReply
    HostReply getHostReply(Integer replyId);
}
