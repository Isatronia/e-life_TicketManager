package com.elife.feature.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公司管理对象 fea_company
 * 
 * @author ishgrina
 * @date 2023-05-14
 */
public class Company extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公司ID */
    private Long companyId;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String companyName;

    /** 公司地址 */
    @Excel(name = "公司地址")
    private String companyAddress;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String email;

    /** 成立时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date foundTime;

    /** 营业执照 */
    @Excel(name = "营业执照")
    private String certificate;

    /** 其他材料 */
    @Excel(name = "其他材料")
    private String additionalInfo;

    /** 申请提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyDate;

    /** 负责人 */
    @Excel(name = "负责人")
    private Long companyMaster;

    public void setCompanyId(Long companyId) 
    {
        this.companyId = companyId;
    }

    public Long getCompanyId() 
    {
        return companyId;
    }
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }

    public String getCompanyName() 
    {
        return companyName;
    }
    public void setCompanyAddress(String companyAddress) 
    {
        this.companyAddress = companyAddress;
    }

    public String getCompanyAddress() 
    {
        return companyAddress;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setFoundTime(Date foundTime) 
    {
        this.foundTime = foundTime;
    }

    public Date getFoundTime() 
    {
        return foundTime;
    }
    public void setCertificate(String certificate) 
    {
        this.certificate = certificate;
    }

    public String getCertificate() 
    {
        return certificate;
    }
    public void setAdditionalInfo(String additionalInfo) 
    {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() 
    {
        return additionalInfo;
    }
    public void setApplyDate(Date applyDate) 
    {
        this.applyDate = applyDate;
    }

    public Date getApplyDate() 
    {
        return applyDate;
    }
    public void setCompanyMaster(Long companyMaster) 
    {
        this.companyMaster = companyMaster;
    }

    public Long getCompanyMaster() 
    {
        return companyMaster;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("companyId", getCompanyId())
            .append("companyName", getCompanyName())
            .append("companyAddress", getCompanyAddress())
            .append("email", getEmail())
            .append("foundTime", getFoundTime())
            .append("certificate", getCertificate())
            .append("additionalInfo", getAdditionalInfo())
            .append("applyDate", getApplyDate())
            .append("companyMaster", getCompanyMaster())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
