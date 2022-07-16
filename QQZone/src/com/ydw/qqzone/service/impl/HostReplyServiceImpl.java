package com.ydw.qqzone.service.impl;

import com.ydw.qqzone.dao.HostReplyDao;
import com.ydw.qqzone.pojo.HostReply;
import com.ydw.qqzone.service.HostReplyService;

/**
 * @author ydw
 * @create 2022-07-15 0:43
 */
public class HostReplyServiceImpl implements HostReplyService {
    private HostReplyDao hostReplyDao;
    @Override
    public HostReply getHostReply(Integer replyId) {
        return hostReplyDao.getHostReply(replyId);
    }
}
