package com.elife.ticket.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.elife.common.annotation.Excel;
import com.elife.common.core.domain.BaseEntity;

/**
 * 投诉对象 fea_complaint
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public class Complaint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 投诉ID */
    private Long complaintId;

    /** 投诉用户 */
    @Excel(name = "投诉用户")
    private Long complaintUser;

    /** 投诉目标 */
    @Excel(name = "投诉目标")
    private Long complaintTarget;

    /** 投诉内容 */
    @Excel(name = "投诉内容")
    private String content;

    public void setComplaintId(Long complaintId) 
    {
        this.complaintId = complaintId;
    }

    public Long getComplaintId() 
    {
        return complaintId;
    }
    public void setComplaintUser(Long complaintUser) 
    {
        this.complaintUser = complaintUser;
    }

    public Long getComplaintUser() 
    {
        return complaintUser;
    }
    public void setComplaintTarget(Long complaintTarget) 
    {
        this.complaintTarget = complaintTarget;
    }

    public Long getComplaintTarget() 
    {
        return complaintTarget;
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
            .append("complaintId", getComplaintId())
            .append("complaintUser", getComplaintUser())
            .append("complaintTarget", getComplaintTarget())
            .append("content", getContent())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
