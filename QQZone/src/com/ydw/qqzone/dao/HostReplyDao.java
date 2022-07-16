package com.ydw.qqzone.dao;

import com.ydw.qqzone.pojo.HostReply;

/**
 * @author ydw
 * @create 2022-07-14 17:35
 */
public interface HostReplyDao {
    // 根据特定回复查看主人回复
    HostReply getHostReply(Integer replyId);

    // 添加主人回复
    void addHostReply(HostReply hostReply);

    // 删除主人回复
    void deleteHostReply(Integer hostReplyId);
}
