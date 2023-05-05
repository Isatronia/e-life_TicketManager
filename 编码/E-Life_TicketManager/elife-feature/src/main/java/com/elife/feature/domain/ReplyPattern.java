package com.elife.feature.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.elife.common.annotation.Excel;
import com.elife.common.core.domain.BaseEntity;

/**
 * 回复模板管理对象 fea_reply_pattern
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public class ReplyPattern extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long patternId;

    /** 公司 */
    @Excel(name = "公司")
    private Long patternCompany;

    /** 标题 */
    @Excel(name = "标题")
    private String patternTitle;

    /** 触发指令 */
    @Excel(name = "触发指令")
    private String patternTrigger;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    public void setPatternId(Long patternId) 
    {
        this.patternId = patternId;
    }

    public Long getPatternId() 
    {
        return patternId;
    }
    public void setPatternCompany(Long patternCompany) 
    {
        this.patternCompany = patternCompany;
    }

    public Long getPatternCompany() 
    {
        return patternCompany;
    }
    public void setPatternTitle(String patternTitle) 
    {
        this.patternTitle = patternTitle;
    }

    public String getPatternTitle() 
    {
        return patternTitle;
    }
    public void setPatternTrigger(String patternTrigger) 
    {
        this.patternTrigger = patternTrigger;
    }

    public String getPatternTrigger() 
    {
        return patternTrigger;
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
            .append("patternId", getPatternId())
            .append("patternCompany", getPatternCompany())
            .append("patternTitle", getPatternTitle())
            .append("patternTrigger", getPatternTrigger())
            .append("content", getContent())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
