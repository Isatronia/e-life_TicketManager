package com.elife.ticket.service.impl;

import java.util.List;

import com.elife.common.annotation.DataScope;
import com.elife.common.constant.UserConstants;
import com.elife.common.core.domain.entity.SysDept;
import com.elife.common.exception.ServiceException;
import com.elife.common.utils.DateUtils;
import com.elife.common.utils.SecurityUtils;
import com.elife.common.utils.uuid.IdUtils;
import com.elife.feature.domain.Company;
import com.elife.feature.domain.TicketType;
import com.elife.feature.mapper.CompanyMapper;
import com.elife.feature.mapper.TicketTypeMapper;
import com.elife.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.ticket.mapper.TicketMapper;
import com.elife.ticket.domain.Ticket;
import com.elife.ticket.service.ITicketService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务单Service业务层处理
 *
 * @author isghrina
 * @date 2023-04-18
 */
@Service
public class TicketServiceImpl implements ITicketService {
    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private TicketTypeMapper ticketTypeMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询服务单
     *
     * @param ticketId 服务单主键
     * @return 服务单
     */
    @Override
    public Ticket selectTicketByTicketId(Long ticketId) {
        return ticketMapper.selectTicketByTicketId(ticketId);
    }

    /**
     * 查询服务单列表
     *
     * @param ticket 服务单
     * @return 服务单
     */
    @Override
    @DataScope(deptAlias = "fea_ticket", userAlias = "fea_ticket")
    public List<Ticket> selectTicketList(Ticket ticket) {
        return ticketMapper.selectTicketList(ticket);
    }

    /**
     * 新增服务单
     *
     * @param ticket 服务单
     * @return 结果
     */
    @Override
    public int insertTicket(Ticket ticket) {
        ticket.setCreateTime(DateUtils.getNowDate());
        return ticketMapper.insertTicket(ticket);
    }

    /**
     * 修改服务单
     *
     * @param ticket 服务单
     * @return 结果
     */
    @Override
    public int updateTicket(Ticket ticket) {
        ticket.setUpdateTime(DateUtils.getNowDate());
        return ticketMapper.updateTicket(ticket);
    }

    /**
     * 批量删除服务单
     *
     * @param ticketIds 需要删除的服务单主键
     * @return 结果
     */
    @Override
    public int deleteTicketByTicketIds(Long[] ticketIds) {
        return ticketMapper.deleteTicketByTicketIds(ticketIds);
    }

    /**
     * 删除服务单信息
     *
     * @param ticketId 服务单主键
     * @return 结果
     */
    @Override
    public int deleteTicketByTicketId(Long ticketId) {
        return ticketMapper.deleteTicketByTicketId(ticketId);
    }

    /**
     * 申请服务单信息
     *
     * @param ticket@return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int applyTicket(Ticket ticket) {
        Company company = companyMapper.selectCompanyByCompanyId(ticket.getCompanyId());
        TicketType type = ticketTypeMapper.selectTicketTypeByTypeId(ticket.getTicketType());
        System.out.println("[DEBUG] received apply, companyId is: " + company.getCompanyId() + "; typeId is: " + type.getTypeId());
        if (!ticketLimitCheck(company)) {
            throw new ServiceException("申请数量过多，请关闭一些服务单后再尝试");
        }
        ticket.setDeptId(type.getDeptId());
        ticket.setStatus(1);

        return ticketMapper.insertTicket(ticket);
    }

    private boolean ticketLimitCheck(Company company) {
        // 获取公司信息
//        Company company = companyMapper.selectCompanyByCompanyId(companyId);
        if (null == company.getTicketLimit()) {
            company.setTicketLimit(2);
        }
        // 创建搜索条件
        Ticket temp = new Ticket();
        temp.setUserId(SecurityUtils.getLoginUser().getUserId());
        temp.setCompanyId(company.getCompanyId());
        // 搜索已申请服务单数量
        Long count = ticketMapper.selectTicketCountByUserAndCompany(temp);
        if (count > company.getTicketLimit()) {
            return false;
        }
        return true;
    }

//    private Long createTicketPermission(Long companyId, Long typeId) {
//        Long targetDeptId;
//        // 获取公司的部门ID以及名称，默认是
//        Company company = companyMapper.selectCompanyByCompanyId(companyId);
//        targetDeptId = company.getDeptId();
//        if (null != typeId) {
//            TicketType type = ticketTypeMapper.selectTicketTypeByTypeId(typeId);
//        }
//
//        SysDept tempDept = deptMapper.selectDeptById(company.getDeptId());
//        if (!UserConstants.DEPT_NORMAL.equals(tempDept.getStatus())) {
//            throw new ServiceException("部门停用，不允许新增");
//        }
//
//        tempDept.setAncestors(tempDept.getAncestors() + "," + tempDept.getDeptId());
//        tempDept.setParentId(tempDept.getDeptId());
//        tempDept.setDeptName(IdUtils.fastSimpleUUID().substring(25));
//
//        // 重置数据, 防止插入出现问题
//        tempDept.setDeptId(null);
//        tempDept.setCreateBy(SecurityUtils.getLoginUser().getUsername());
//        tempDept.setCreateTime(DateUtils.getNowDate());
//
//        System.out.println("[DEBUG]" + tempDept.toString());
//        deptMapper.insertDept(tempDept);
//        return deptMapper.selectLastInsertId();
//    }

}
