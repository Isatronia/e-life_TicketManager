package com.elife.feature.service.impl;

import java.util.List;
import com.elife.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.feature.mapper.ShopItemMapper;
import com.elife.feature.domain.ShopItem;
import com.elife.feature.service.IShopItemService;

/**
 * 商品信息Service业务层处理
 *
 * @author ishgrina
 * @date 2023-05-15
 */
@Service
public class ShopItemServiceImpl implements IShopItemService
{
    @Autowired
    private ShopItemMapper shopItemMapper;

    /**
     * 查询商品信息
     *
     * @param recordId 商品信息主键
     * @return 商品信息
     */
    @Override
    public ShopItem selectShopItemByRecordId(Long recordId)
    {
        return shopItemMapper.selectShopItemByRecordId(recordId);
    }

    /**
     * 查询商品信息
     *
     * @param userId 用户编号
     * @return 商品信息
     */
    @Override
    public List<ShopItem> selectShopItemByUserId(Long userId) {
        return null;
    }

    /**
     * 查询商品信息
     *
     * @param companyId 公司编号
     * @return 商品信息
     */
    @Override
    public List<ShopItem> selectShopItemByCompanyId(Long companyId) {
        return null;
    }

    /**
     * 查询商品信息列表
     *
     * @param shopItem 商品信息
     * @return 商品信息
     */
    @Override
    public List<ShopItem> selectShopItemList(ShopItem shopItem)
    {
        return shopItemMapper.selectShopItemList(shopItem);
    }

    /**
     * 新增商品信息
     *
     * @param shopItem 商品信息
     * @return 结果
     */
    @Override
    public int insertShopItem(ShopItem shopItem)
    {
        shopItem.setCreateTime(DateUtils.getNowDate());
        return shopItemMapper.insertShopItem(shopItem);
    }

    /**
     * 修改商品信息
     *
     * @param shopItem 商品信息
     * @return 结果
     */
    @Override
    public int updateShopItem(ShopItem shopItem)
    {
        shopItem.setUpdateTime(DateUtils.getNowDate());
        return shopItemMapper.updateShopItem(shopItem);
    }

    /**
     * 批量删除商品信息
     *
     * @param recordIds 需要删除的商品信息主键
     * @return 结果
     */
    @Override
    public int deleteShopItemByRecordIds(Long[] recordIds)
    {
        return shopItemMapper.deleteShopItemByRecordIds(recordIds);
    }

    /**
     * 删除商品信息信息
     *
     * @param recordId 商品信息主键
     * @return 结果
     */
    @Override
    public int deleteShopItemByRecordId(Long recordId)
    {
        return shopItemMapper.deleteShopItemByRecordId(recordId);
    }
}
