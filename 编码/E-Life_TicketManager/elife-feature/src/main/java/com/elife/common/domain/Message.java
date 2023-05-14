package com.elife.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.elife.common.annotation.Excel;
import com.elife.common.core.domain.BaseEntity;

/**
 * 消息查询对象 fea_message
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public class Message extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long messageId;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private Long messageType;

    /** 所属服务单 */
    @Excel(name = "所属服务单")
    private Long messageTicket;

    /** 发送者 */
    private Long author;

    /** 引用的消息 */
    @Excel(name = "引用的消息")
    private Long quote;

    /** 提及的用户ID */
    @Excel(name = "提及的用户ID")
    private String mention;

    /** 当前状态 */
    @Excel(name = "当前状态")
    private Integer status;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String content;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }
    public void setMessageType(Long messageType) 
    {
        this.messageType = messageType;
    }

    public Long getMessageType() 
    {
        return messageType;
    }
    public void setMessageTicket(Long messageTicket) 
    {
        this.messageTicket = messageTicket;
    }

    public Long getMessageTicket() 
    {
        return messageTicket;
    }
    public void setAuthor(Long author) 
    {
        this.author = author;
    }

    public Long getAuthor() 
    {
        return author;
    }
    public void setQuote(Long quote) 
    {
        this.quote = quote;
    }

    public Long getQuote() 
    {
        return quote;
    }
    public void setMention(String mention) 
    {
        this.mention = mention;
    }

    public String getMention() 
    {
        return mention;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("messageId", getMessageId())
            .append("messageType", getMessageType())
            .append("messageTicket", getMessageTicket())
            .append("author", getAuthor())
            .append("quote", getQuote())
            .append("mention", getMention())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("content", getContent())
            .toString();
    }
}
