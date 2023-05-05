package com.elife.feature.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.elife.common.annotation.Excel;
import com.elife.common.core.domain.BaseEntity;

/**
 * 订阅对象 fea_user_company
 *
 * @author Ishgrina
 * @date 2023-04-20
 */
public class Subscribe extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 公司ID */
    private Long companyId;

    /** 公司名 */
    private String companyName;

    /** 用户名 */
    private String userName;

    public Subscribe(Long userId, Long companyId) {
        super();
        this.userId = userId;
        this.companyId = companyId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setCompanyId(Long companyId)
    {
        this.companyId = companyId;
    }

    public Long getCompanyId()
    {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userMame) {
        this.userName = userMame;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("companyId", getCompanyId())
                .append("userName", getUserName())
                .append("companyName", getCompanyName())
                .toString();
    }
}
