package com.elife.feature.service.impl;

import java.util.List;
import com.elife.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.feature.mapper.ReplyPatternMapper;
import com.elife.feature.domain.ReplyPattern;
import com.elife.feature.service.IReplyPatternService;

/**
 * 回复模板管理Service业务层处理
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
@Service
public class ReplyPatternServiceImpl implements IReplyPatternService 
{
    @Autowired
    private ReplyPatternMapper replyPatternMapper;

    /**
     * 查询回复模板管理
     * 
     * @param patternId 回复模板管理主键
     * @return 回复模板管理
     */
    @Override
    public ReplyPattern selectReplyPatternByPatternId(Long patternId)
    {
        return replyPatternMapper.selectReplyPatternByPatternId(patternId);
    }

    /**
     * 查询回复模板管理列表
     * 
     * @param replyPattern 回复模板管理
     * @return 回复模板管理
     */
    @Override
    public List<ReplyPattern> selectReplyPatternList(ReplyPattern replyPattern)
    {
        return replyPatternMapper.selectReplyPatternList(replyPattern);
    }

    /**
     * 新增回复模板管理
     * 
     * @param replyPattern 回复模板管理
     * @return 结果
     */
    @Override
    public int insertReplyPattern(ReplyPattern replyPattern)
    {
        replyPattern.setCreateTime(DateUtils.getNowDate());
        return replyPatternMapper.insertReplyPattern(replyPattern);
    }

    /**
     * 修改回复模板管理
     * 
     * @param replyPattern 回复模板管理
     * @return 结果
     */
    @Override
    public int updateReplyPattern(ReplyPattern replyPattern)
    {
        replyPattern.setUpdateTime(DateUtils.getNowDate());
        return replyPatternMapper.updateReplyPattern(replyPattern);
    }

    /**
     * 批量删除回复模板管理
     * 
     * @param patternIds 需要删除的回复模板管理主键
     * @return 结果
     */
    @Override
    public int deleteReplyPatternByPatternIds(Long[] patternIds)
    {
        return replyPatternMapper.deleteReplyPatternByPatternIds(patternIds);
    }

    /**
     * 删除回复模板管理信息
     * 
     * @param patternId 回复模板管理主键
     * @return 结果
     */
    @Override
    public int deleteReplyPatternByPatternId(Long patternId)
    {
        return replyPatternMapper.deleteReplyPatternByPatternId(patternId);
    }
}
