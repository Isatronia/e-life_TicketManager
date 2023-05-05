package com.elife.feature.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.feature.mapper.BlockMapper;
import com.elife.feature.domain.Block;
import com.elife.feature.service.IBlockService;

/**
 * 黑名单Service业务层处理
 *
 * @author ishgrina
 * @date 2023-05-05
 */
@Service
public class BlockServiceImpl implements IBlockService
{
    @Autowired
    private BlockMapper blockMapper;

    /**
     * 查询黑名单
     *
     * @param userId 黑名单主键
     * @return 黑名单
     */
    @Override
    public Block selectBlockByUserId(Long userId)
    {
        return blockMapper.selectBlockByUserId(userId);
    }

    /**
     * 查询黑名单列表
     *
     * @param block 黑名单
     * @return 黑名单
     */
    @Override
    public List<Block> selectBlockList(Block block)
    {
        return blockMapper.selectBlockList(block);
    }

    /**
     * 新增黑名单
     *
     * @param block 黑名单
     * @return 结果
     */
    @Override
    public int insertBlock(Block block)
    {
        return blockMapper.insertBlock(block);
    }

    /**
     * 修改黑名单
     *
     * @param block 黑名单
     * @return 结果
     */
    @Override
    public int updateBlock(Block block)
    {
        return blockMapper.updateBlock(block);
    }

    /**
     * 批量删除黑名单
     *
     * @param userIds 需要删除的黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBlockByUserIds(Long[] userIds)
    {
        return blockMapper.deleteBlockByUserIds(userIds);
    }

    /**
     * 删除黑名单信息
     *
     * @param userId 黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBlockByUserId(Long userId)
    {
        return blockMapper.deleteBlockByUserId(userId);
    }
}
