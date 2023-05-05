package com.elife.feature.service;

import java.util.List;
import com.elife.feature.domain.TicketType;

/**
 * 问题类型Service接口
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public interface ITicketTypeService 
{
    /**
     * 查询问题类型
     * 
     * @param typeId 问题类型主键
     * @return 问题类型
     */
    public TicketType selectTicketTypeByTypeId(Long typeId);

    /**
     * 查询问题类型列表
     * 
     * @param ticketType 问题类型
     * @return 问题类型集合
     */
    public List<TicketType> selectTicketTypeList(TicketType ticketType);

    /**
     * 新增问题类型
     * 
     * @param ticketType 问题类型
     * @return 结果
     */
    public int insertTicketType(TicketType ticketType);

    /**
     * 修改问题类型
     * 
     * @param ticketType 问题类型
     * @return 结果
     */
    public int updateTicketType(TicketType ticketType);

    /**
     * 批量删除问题类型
     * 
     * @param typeIds 需要删除的问题类型主键集合
     * @return 结果
     */
    public int deleteTicketTypeByTypeIds(Long[] typeIds);

    /**
     * 删除问题类型信息
     * 
     * @param typeId 问题类型主键
     * @return 结果
     */
    public int deleteTicketTypeByTypeId(Long typeId);
}
