package com.elife.feature.service;

import java.util.List;
import com.elife.feature.domain.ShopItem;

/**
 * 商品信息Service接口
 *
 * @author ishgrina
 * @date 2023-05-15
 */
public interface IShopItemService
{
    /**
     * 查询商品信息
     *
     * @param recordId 商品信息主键
     * @return 商品信息
     */
    public ShopItem selectShopItemByRecordId(Long recordId);

    /**
     * 查询商品信息
     *
     * @param userId 用户编号
     * @return 商品信息
     */
    public List<ShopItem> selectShopItemByUserId(Long userId);

    /**
     * 查询商品信息
     *
     * @param companyId 公司编号
     * @return 商品信息
     */
    public List<ShopItem> selectShopItemByCompanyId(Long companyId);

    /**
     * 查询商品信息列表
     *
     * @param shopItem 商品信息
     * @return 商品信息集合
     */
    public List<ShopItem> selectShopItemList(ShopItem shopItem);

    /**
     * 新增商品信息
     *
     * @param shopItem 商品信息
     * @return 结果
     */
    public int insertShopItem(ShopItem shopItem);

    /**
     * 修改商品信息
     *
     * @param shopItem 商品信息
     * @return 结果
     */
    public int updateShopItem(ShopItem shopItem);

    /**
     * 批量删除商品信息
     *
     * @param recordIds 需要删除的商品信息主键集合
     * @return 结果
     */
    public int deleteShopItemByRecordIds(Long[] recordIds);

    /**
     * 删除商品信息信息
     *
     * @param recordId 商品信息主键
     * @return 结果
     */
    public int deleteShopItemByRecordId(Long recordId);
}
