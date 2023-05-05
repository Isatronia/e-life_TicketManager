package com.elife.ticket.mapper;

import java.util.List;
import com.elife.ticket.domain.Ticket;

/**
 * 服务单Mapper接口
 * 
 * @author isghrina
 * @date 2023-04-18
 */
public interface TicketMapper 
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
     * 查询用户在该公司申请的服务单数量
     *
     * @param ticket
     * @return
     */
    public Long selectTicketCountByUserAndCompany(Ticket ticket);

    /**
     * 查询上一个插入的数据ID
     *
     * @return ID
     */
    public Long selectLastInsertId();

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
     * 删除服务单
     * 
     * @param ticketId 服务单主键
     * @return 结果
     */
    public int deleteTicketByTicketId(Long ticketId);

    /**
     * 批量删除服务单
     * 
     * @param ticketIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTicketByTicketIds(Long[] ticketIds);
}
