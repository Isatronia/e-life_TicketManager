package com.elife.feature.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.elife.common.annotation.Excel;
import com.elife.common.core.domain.BaseEntity;

/**
 * 黑名单对象 fea_block
 *
 * @author ishgrina
 * @date 2023-05-05
 */
public class Block extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 黑名单ID */
    private Long targetId;

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setTargetId(Long targetId)
    {
        this.targetId = targetId;
    }

    public Long getTargetId()
    {
        return targetId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("targetId", getTargetId())
                .toString();
    }
}
