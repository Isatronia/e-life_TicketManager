package com.elife.common.service.impl;

import java.util.List;
import com.elife.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.common.mapper.MessageMapper;
import com.elife.common.domain.Message;
import com.elife.common.service.IMessageService;

/**
 * 消息查询Service业务层处理
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
@Service
public class MessageServiceImpl implements IMessageService 
{
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询消息查询
     * 
     * @param messageId 消息查询主键
     * @return 消息查询
     */
    @Override
    public Message selectMessageByMessageId(Long messageId)
    {
        return messageMapper.selectMessageByMessageId(messageId);
    }

    /**
     * 查询消息查询列表
     * 
     * @param message 消息查询
     * @return 消息查询
     */
    @Override
    public List<Message> selectMessageList(Message message)
    {
        return messageMapper.selectMessageList(message);
    }

    /**
     * 查询服务单消息列表
     *
     * @param ticketId 服务单ID
     * @return 消息查询
     */
    @Override
    public List<Message> selectMessageListByTicketId(Long ticketId) {
        Message msg = new Message();
        msg.setMessageTicket(ticketId);
        msg.setMessageType(0l);
        return messageMapper.selectMessageList(msg);
    }

    @Override
    public List<Message> selectDirectMessage(Long userId) {
        Message msg = new Message();
        msg.setMessageTicket(userId);
        msg.setMessageType(1l);
        return messageMapper.selectMessageList(msg);
    }

    /**
     * 新增消息查询
     * 
     * @param message 消息查询
     * @return 结果
     */
    @Override
    public int insertMessage(Message message)
    {
        message.setCreateTime(DateUtils.getNowDate());
        return messageMapper.insertMessage(message);
    }

    /**
     * 修改消息查询
     * 
     * @param message 消息查询
     * @return 结果
     */
    @Override
    public int updateMessage(Message message)
    {
        message.setUpdateTime(DateUtils.getNowDate());
        return messageMapper.updateMessage(message);
    }

    /**
     * 批量删除消息查询
     * 
     * @param messageIds 需要删除的消息查询主键
     * @return 结果
     */
    @Override
    public int deleteMessageByMessageIds(Long[] messageIds)
    {
        return messageMapper.deleteMessageByMessageIds(messageIds);
    }

    /**
     * 删除消息查询信息
     * 
     * @param messageId 消息查询主键
     * @return 结果
     */
    @Override
    public int deleteMessageByMessageId(Long messageId)
    {
        return messageMapper.deleteMessageByMessageId(messageId);
    }
}
