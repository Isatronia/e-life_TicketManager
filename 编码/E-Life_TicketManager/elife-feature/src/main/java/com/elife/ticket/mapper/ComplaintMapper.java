package com.elife.ticket.mapper;

import java.util.List;
import com.elife.ticket.domain.Complaint;

/**
 * 投诉Mapper接口
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public interface ComplaintMapper 
{
    /**
     * 查询投诉
     * 
     * @param complaintId 投诉主键
     * @return 投诉
     */
    public Complaint selectComplaintByComplaintId(Long complaintId);

    /**
     * 查询投诉列表
     * 
     * @param complaint 投诉
     * @return 投诉集合
     */
    public List<Complaint> selectComplaintList(Complaint complaint);

    /**
     * 新增投诉
     * 
     * @param complaint 投诉
     * @return 结果
     */
    public int insertComplaint(Complaint complaint);

    /**
     * 修改投诉
     * 
     * @param complaint 投诉
     * @return 结果
     */
    public int updateComplaint(Complaint complaint);

    /**
     * 删除投诉
     * 
     * @param complaintId 投诉主键
     * @return 结果
     */
    public int deleteComplaintByComplaintId(Long complaintId);

    /**
     * 批量删除投诉
     * 
     * @param complaintIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteComplaintByComplaintIds(Long[] complaintIds);
}
