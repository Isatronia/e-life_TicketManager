package com.elife.ticket.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.elife.common.annotation.Excel;
import com.elife.common.core.domain.BaseEntity;

/**
 * 服务单对象 fea_ticket
 * 
 * @author isghrina
 * @date 2023-04-18
 */
public class Ticket extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long ticketId;

    /** 父单ID */
    @Excel(name = "父单ID")
    private Long parentId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;

    /** 用户ID, 用于申请数量限制 */
    @Excel(name = "申请用户")
    private Long userId;

    /** 部门ID, 用于权限控制 */
    @Excel(name = "服务单权限部门")
    private Long deptId;

    /** 公司ID, 用于控制申请数量 */
    @Excel(name = "服务单所属公司")
    private Long companyId;

    /** 服务单标题 */
    @Excel(name = "服务单标题")
    private String ticketTitle;

    /** 服务单类型 */
    @Excel(name = "服务单类型")
    private Long ticketType;

    /** 服务单状态 */
    @Excel(name = "服务单状态")
    private Integer status;

    /** 权限信息 */
    @Excel(name = "权限信息")
    private String authorizationInfo;

    /** 评价信息 */
    @Excel(name = "评价信息")
    private Integer commitInfo;

    public void setTicketId(Long ticketId) 
    {
        this.ticketId = ticketId;
    }

    public Long getTicketId() 
    {
        return ticketId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setAncestors(String ancestors) 
    {
        this.ancestors = ancestors;
    }

    public String getAncestors() 
    {
        return ancestors;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setTicketTitle(String ticketTitle)
    {
        this.ticketTitle = ticketTitle;
    }

    public String getTicketTitle() 
    {
        return ticketTitle;
    }
    public void setTicketType(Long ticketType) 
    {
        this.ticketType = ticketType;
    }

    public Long getTicketType() 
    {
        return ticketType;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setAuthorizationInfo(String authorizationInfo) 
    {
        this.authorizationInfo = authorizationInfo;
    }

    public String getAuthorizationInfo() 
    {
        return authorizationInfo;
    }
    public void setCommitInfo(Integer commitInfo) 
    {
        this.commitInfo = commitInfo;
    }

    public Integer getCommitInfo() 
    {
        return commitInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ticketId", getTicketId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("companyId", getCompanyId())
            .append("ticketTitle", getTicketTitle())
            .append("ticketType", getTicketType())
            .append("status", getStatus())
            .append("authorizationInfo", getAuthorizationInfo())
            .append("commitInfo", getCommitInfo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
