package com.elife.feature.service;

import java.util.List;
import com.elife.feature.domain.ReplyPattern;

/**
 * 回复模板管理Service接口
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
public interface IReplyPatternService 
{
    /**
     * 查询回复模板管理
     * 
     * @param patternId 回复模板管理主键
     * @return 回复模板管理
     */
    public ReplyPattern selectReplyPatternByPatternId(Long patternId);

    /**
     * 查询回复模板管理列表
     * 
     * @param replyPattern 回复模板管理
     * @return 回复模板管理集合
     */
    public List<ReplyPattern> selectReplyPatternList(ReplyPattern replyPattern);

    /**
     * 新增回复模板管理
     * 
     * @param replyPattern 回复模板管理
     * @return 结果
     */
    public int insertReplyPattern(ReplyPattern replyPattern);

    /**
     * 修改回复模板管理
     * 
     * @param replyPattern 回复模板管理
     * @return 结果
     */
    public int updateReplyPattern(ReplyPattern replyPattern);

    /**
     * 批量删除回复模板管理
     * 
     * @param patternIds 需要删除的回复模板管理主键集合
     * @return 结果
     */
    public int deleteReplyPatternByPatternIds(Long[] patternIds);

    /**
     * 删除回复模板管理信息
     * 
     * @param patternId 回复模板管理主键
     * @return 结果
     */
    public int deleteReplyPatternByPatternId(Long patternId);
}
