package com.elife.feature.service.impl;

import java.util.List;

import com.elife.common.annotation.DataScope;
import com.elife.common.utils.DateUtils;
import com.elife.feature.domain.Subscribe;
import com.elife.feature.mapper.SubscribeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.feature.mapper.CompanyMapper;
import com.elife.feature.domain.Company;
import com.elife.feature.service.ICompanyService;

import static com.elife.common.utils.SecurityUtils.getLoginUser;

/**
 * 公司管理Service业务层处理
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
@Service
public class CompanyServiceImpl implements ICompanyService 
{
    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SubscribeMapper subscribeMapper;

    /**
     * 查询公司管理
     * 
     * @param companyId 公司管理主键
     * @return 公司管理
     */
    @Override
    public Company selectCompanyByCompanyId(Long companyId)
    {
        return companyMapper.selectCompanyByCompanyId(companyId);
    }

    /**
     * 查询公司管理列表
     * 
     * @param company 公司管理
     * @return 公司管理
     */
    @Override
//    @DataScope(deptAlias = "fea_company")
    public List<Company> selectCompanyList(Company company)
    {
        return companyMapper.selectCompanyList(company);
    }

    /**
     * 新增公司管理
     * 
     * @param company 公司管理
     * @return 结果
     */
    @Override
    public int insertCompany(Company company)
    {
        company.setCreateTime(DateUtils.getNowDate());
        return companyMapper.insertCompany(company);
    }

    /**
     * 修改公司信息
     * 
     * @param company 公司信息
     * @return 结果
     */
    @Override
    public int updateCompany(Company company)
    {
        company.setUpdateTime(DateUtils.getNowDate());
        return companyMapper.updateCompany(company);
    }

    /**
     * 批量删除公司管理
     * 
     * @param companyIds 需要删除的公司管理主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByCompanyIds(Long[] companyIds)
    {
        return companyMapper.deleteCompanyByCompanyIds(companyIds);
    }

    /**
     * 删除公司管理信息
     * 
     * @param companyId 公司管理主键
     * @return 结果
     */
    @Override
    public int deleteCompanyByCompanyId(Long companyId)
    {
        return companyMapper.deleteCompanyByCompanyId(companyId);
    }

}
