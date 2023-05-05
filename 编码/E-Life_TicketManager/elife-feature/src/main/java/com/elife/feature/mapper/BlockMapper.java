package com.elife.feature.mapper;

import java.util.List;
import com.elife.feature.domain.Block;

/**
 * 黑名单Mapper接口
 *
 * @author ishgrina
 * @date 2023-05-05
 */
public interface BlockMapper
{
    /**
     * 查询黑名单
     *
     * @param userId 黑名单主键
     * @return 黑名单
     */
    public Block selectBlockByUserId(Long userId);

    /**
     * 查询黑名单列表
     *
     * @param block 黑名单
     * @return 黑名单集合
     */
    public List<Block> selectBlockList(Block block);

    /**
     * 新增黑名单
     *
     * @param block 黑名单
     * @return 结果
     */
    public int insertBlock(Block block);

    /**
     * 修改黑名单
     *
     * @param block 黑名单
     * @return 结果
     */
    public int updateBlock(Block block);

    /**
     * 删除黑名单
     *
     * @param userId 黑名单主键
     * @return 结果
     */
    public int deleteBlockByUserId(Long userId);

    /**
     * 批量删除黑名单
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBlockByUserIds(Long[] userIds);
}
