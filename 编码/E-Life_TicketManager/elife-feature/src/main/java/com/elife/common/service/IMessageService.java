package com.elife.common.service;

import java.util.List;
import com.elife.common.domain.Message;

/**
 * 消息查询Service接口
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public interface IMessageService 
{
    /**
     * 查询消息查询
     * 
     * @param messageId 消息查询主键
     * @return 消息查询
     */
    public Message selectMessageByMessageId(Long messageId);

    /**
     * 查询消息查询列表
     * 
     * @param message 消息查询
     * @return 消息查询集合
     */
    public List<Message> selectMessageList(Message message);

    /**
     * 查询消息查询列表
     *
     * @param message 消息查询
     * @return 消息查询集合
     */
    public List<Message> selectMessageListByTicketId(Long ticketId);

    /**
     * 查询DM列表
     *
     * @param userId 用户Id
     * @return 消息查询集合
     */
    public List<Message> selectDirectMessage(Long userId);

    /**
     * 新增消息查询
     * 
     * @param message 消息查询
     * @return 结果
     */
    public int insertMessage(Message message);

    /**
     * 修改消息查询
     * 
     * @param message 消息查询
     * @return 结果
     */
    public int updateMessage(Message message);

    /**
     * 批量删除消息查询
     * 
     * @param messageIds 需要删除的消息查询主键集合
     * @return 结果
     */
    public int deleteMessageByMessageIds(Long[] messageIds);

    /**
     * 删除消息查询信息
     * 
     * @param messageId 消息查询主键
     * @return 结果
     */
    public int deleteMessageByMessageId(Long messageId);
}
