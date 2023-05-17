package com.elife.feature.mapper;

import java.util.List;
import com.elife.feature.domain.Company;

/**
 * 公司管理Mapper接口
 * 
 * @author ishgrina
 * @date 2023-05-14
 */
public interface CompanyMapper 
{
    /**
     * 查询公司管理
     * 
     * @param companyId 公司管理主键
     * @return 公司管理
     */
    public Company selectCompanyByCompanyId(Long companyId);

    /**
     * 查询公司管理列表
     * 
     * @param company 公司管理
     * @return 公司管理集合
     */
    public List<Company> selectCompanyList(Company company);

    /**
     * 新增公司管理
     * 
     * @param company 公司管理
     * @return 结果
     */
    public int insertCompany(Company company);

    /**
     * 修改公司管理
     * 
     * @param company 公司管理
     * @return 结果
     */
    public int updateCompany(Company company);

    /**
     * 删除公司管理
     * 
     * @param companyId 公司管理主键
     * @return 结果
     */
    public int deleteCompanyByCompanyId(Long companyId);

    /**
     * 批量删除公司管理
     * 
     * @param companyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompanyByCompanyIds(Long[] companyIds);
}
