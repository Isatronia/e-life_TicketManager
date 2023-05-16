package com.elife.feature.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.elife.common.annotation.Excel;
import com.elife.common.core.domain.BaseEntity;

/**
 * 商品信息对象 fea_item
 *
 * @author ishgrina
 * @date 2023-05-15
 */
public class ShopItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录编号 */
    private Long recordId;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private Long userId;

    /** 公司编号 */
    @Excel(name = "公司编号")
    private Long companyId;

    /** 部门编号 */
    @Excel(name = "部门编号")
    private Long deptId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String itemName;

    /** 商品图片 */
    @Excel(name = "商品图片")
    private String avatar;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String description;

    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

    public Long getRecordId()
    {
        return recordId;
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
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemName()
    {
        return itemName;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("recordId", getRecordId())
                .append("userId", getUserId())
                .append("companyId", getCompanyId())
                .append("deptId", getDeptId())
                .append("itemName", getItemName())
                .append("avatar", getAvatar())
                .append("description", getDescription())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .toString();
    }
}
