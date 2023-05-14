package com.elife.feature.service.impl;

import java.util.List;

import com.elife.common.constant.UserConstants;
import com.elife.common.core.domain.entity.SysDept;
import com.elife.common.exception.ServiceException;
import com.elife.common.utils.DateUtils;
import com.elife.common.utils.SecurityUtils;
import com.elife.common.utils.uuid.IdUtils;
import com.elife.feature.domain.Company;
import com.elife.feature.mapper.CompanyMapper;
import com.elife.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.feature.mapper.TicketTypeMapper;
import com.elife.feature.domain.TicketType;
import com.elife.feature.service.ITicketTypeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 问题类型Service业务层处理
 *
 * @author ishgrina
 * @date 2023-04-18
 */
@Service
public class TicketTypeServiceImpl implements ITicketTypeService {
    @Autowired
    private TicketTypeMapper ticketTypeMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询问题类型
     *
     * @param typeId 问题类型主键
     * @return 问题类型
     */
    @Override
    public TicketType selectTicketTypeByTypeId(Long typeId) {
        return ticketTypeMapper.selectTicketTypeByTypeId(typeId);
    }

    /**
     * 查询问题类型列表
     *
     * @param ticketType 问题类型
     * @return 问题类型
     */
    @Override
    public List<TicketType> selectTicketTypeList(TicketType ticketType) {
        return ticketTypeMapper.selectTicketTypeList(ticketType);
    }

    /**
     * 新增问题类型
     *
     * @param ticketType 问题类型
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTicketType(TicketType ticketType) {
        // 检测同一公司类型名称唯一
        TicketType temp = new TicketType();
        temp.setTypeName(ticketType.getTypeName());
        temp.setTypeCompany(ticketType.getTypeCompany());
        if (0 != ticketTypeMapper.selectTicketTypeList(temp).size()) {
            throw new ServiceException("类型名称不能重复！");
        }

        // 开始新增
        Long deptId = createTypePermission(ticketType.getTypeCompany());

        ticketType.setDeptId(deptId);
        ticketType.setCreateTime(DateUtils.getNowDate());

        SysDept dept = deptMapper.selectDeptById(deptId);
        dept.setDeptName(ticketType.getTypeName());
        deptMapper.updateDept(dept);
        return ticketTypeMapper.insertTicketType(ticketType);
    }

    /**
     * 修改问题类型
     *
     * @param ticketType 问题类型
     * @return 结果
     */
    @Override
    public int updateTicketType(TicketType ticketType) {
        ticketType.setUpdateTime(DateUtils.getNowDate());
        return ticketTypeMapper.updateTicketType(ticketType);
    }

    /**
     * 批量删除问题类型
     *
     * @param typeIds 需要删除的问题类型主键
     * @return 结果
     */
    @Override
    public int deleteTicketTypeByTypeIds(Long[] typeIds) {
        return ticketTypeMapper.deleteTicketTypeByTypeIds(typeIds);
    }

    /**
     * 删除问题类型信息
     *
     * @param typeId 问题类型主键
     * @return 结果
     */
    @Override
    public int deleteTicketTypeByTypeId(Long typeId) {
        return ticketTypeMapper.deleteTicketTypeByTypeId(typeId);
    }


    private Long createTypePermission(Long companyId) {
        Long targetDeptId;
        // 获取公司的信息
        Company company = companyMapper.selectCompanyByCompanyId(companyId);
        targetDeptId = company.getDeptId();

        SysDept tempDept = deptMapper.selectDeptById(company.getDeptId());
        if (!UserConstants.DEPT_NORMAL.equals(tempDept.getStatus())) {
            throw new ServiceException("类型停用，不允许新增");
        }

        tempDept.setAncestors(tempDept.getAncestors() + "," + tempDept.getDeptId());
        tempDept.setParentId(tempDept.getDeptId());
        tempDept.setDeptName(IdUtils.fastSimpleUUID().substring(25));

        // 重置数据, 防止插入出现问题
        tempDept.setDeptId(null);
        tempDept.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        tempDept.setCreateTime(DateUtils.getNowDate());

        System.out.println("[DEBUG]" + tempDept.toString());
        deptMapper.insertDept(tempDept);
        return deptMapper.selectLastInsertId();
    }
}
