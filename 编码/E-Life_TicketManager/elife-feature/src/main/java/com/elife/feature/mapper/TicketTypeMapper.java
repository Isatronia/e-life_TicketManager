package com.elife.feature.mapper;

import java.util.List;
import com.elife.feature.domain.TicketType;

/**
 * 问题类型Mapper接口
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public interface TicketTypeMapper 
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
     * 删除问题类型
     * 
     * @param typeId 问题类型主键
     * @return 结果
     */
    public int deleteTicketTypeByTypeId(Long typeId);

    /**
     * 批量删除问题类型
     * 
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTicketTypeByTypeIds(Long[] typeIds);
}
