package com.elife.feature.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.elife.common.annotation.Excel;
import com.elife.common.core.domain.BaseEntity;

/**
 * 问题类型对象 fea_type
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public class TicketType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long typeId;

    /** 所属公司 */
    @Excel(name = "所属公司")
    private Long typeCompany;

    @Excel(name = "所属公司")
    private Long deptId;

    /** 名称 */
    @Excel(name = "名称")
    private String typeName;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setTypeCompany(Long typeCompany) 
    {
        this.typeCompany = typeCompany;
    }

    public Long getTypeCompany() 
    {
        return typeCompany;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
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
            .append("typeId", getTypeId())
            .append("typeCompany", getTypeCompany())
            .append("deptId", getDeptId())
            .append("typeName", getTypeName())
            .append("description", getDescription())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
