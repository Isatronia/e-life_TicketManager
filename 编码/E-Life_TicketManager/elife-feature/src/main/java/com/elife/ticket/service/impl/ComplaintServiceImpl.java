package com.elife.ticket.service.impl;

import java.util.List;

import com.elife.common.annotation.DataScope;
import com.elife.common.core.domain.entity.SysUser;
import com.elife.common.utils.DateUtils;
import com.elife.feature.domain.Company;
import com.elife.feature.mapper.CompanyMapper;
import com.elife.feature.mapper.SubscribeMapper;
import com.elife.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.ticket.mapper.ComplaintMapper;
import com.elife.ticket.domain.Complaint;
import com.elife.ticket.service.IComplaintService;

/**
 * 投诉Service业务层处理
 *
 * @author ishgrina
 * @date 2023-04-18
 */
@Service
public class ComplaintServiceImpl implements IComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SubscribeMapper subscribeMapper;

    /**
     * 查询投诉
     *
     * @param complaintId 投诉主键
     * @return 投诉
     */
    @Override
    public Complaint selectComplaintByComplaintId(Long complaintId) {
        return complaintMapper.selectComplaintByComplaintId(complaintId);
    }

    /**
     * 查询投诉列表
     *
     * @param complaint 投诉
     * @return 投诉
     */
    @Override
    @DataScope(deptAlias = "fea_complaint")
    public List<Complaint> selectComplaintList(Complaint complaint) {
        return complaintMapper.selectComplaintList(complaint);
    }

    /**
     * 新增投诉
     *
     * @param complaint 投诉
     * @return 结果
     */
    @Override
    public int insertComplaint(Complaint complaint) {
        Company company = companyMapper.selectCompanyByCompanyId(subscribeMapper.selectSubscribeByUserId(complaint.getComplaintTarget()).getCompanyId());
        complaint.setDeptId(company.getDeptId());
        complaint.setCreateTime(DateUtils.getNowDate());
        return complaintMapper.insertComplaint(complaint);
    }

    /**
     * 修改投诉
     *
     * @param complaint 投诉
     * @return 结果
     */
    @Override
    public int updateComplaint(Complaint complaint) {
        complaint.setUpdateTime(DateUtils.getNowDate());
        return complaintMapper.updateComplaint(complaint);
    }

    /**
     * 批量删除投诉
     *
     * @param complaintIds 需要删除的投诉主键
     * @return 结果
     */
    @Override
    public int deleteComplaintByComplaintIds(Long[] complaintIds) {
        return complaintMapper.deleteComplaintByComplaintIds(complaintIds);
    }

    /**
     * 删除投诉信息
     *
     * @param complaintId 投诉主键
     * @return 结果
     */
    @Override
    public int deleteComplaintByComplaintId(Long complaintId) {
        return complaintMapper.deleteComplaintByComplaintId(complaintId);
    }
}
