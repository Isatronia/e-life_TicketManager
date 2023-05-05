package com.elife.ticket.service;

import java.util.List;
import com.elife.ticket.domain.Ticket;

/**
 * 服务单Service接口
 * 
 * @author isghrina
 * @date 2023-04-18
 */
public interface ITicketService 
{
    /**
     * 查询服务单
     * 
     * @param ticketId 服务单主键
     * @return 服务单
     */
    public Ticket selectTicketByTicketId(Long ticketId);

    /**
     * 查询服务单列表
     * 
     * @param ticket 服务单
     * @return 服务单集合
     */
    public List<Ticket> selectTicketList(Ticket ticket);

    /**
     * 新增服务单
     * 
     * @param ticket 服务单
     * @return 结果
     */
    public int insertTicket(Ticket ticket);

    /**
     * 修改服务单
     * 
     * @param ticket 服务单
     * @return 结果
     */
    public int updateTicket(Ticket ticket);

    /**
     * 批量删除服务单
     * 
     * @param ticketIds 需要删除的服务单主键集合
     * @return 结果
     */
    public int deleteTicketByTicketIds(Long[] ticketIds);

    /**
     * 删除服务单信息
     * 
     * @param ticketId 服务单主键
     * @return 结果
     */
    public int deleteTicketByTicketId(Long ticketId);

    /**
     * 申请服务单信息
     *
     * @param companyId 公司主键
     * @param ticketTypeId 服务单类型主键
     * @return 结果
     */
    public int applyTicket(Long companyId, Long ticketTypeId);
}
