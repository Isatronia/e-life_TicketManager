package com.elife.feature.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.elife.common.annotation.Log;
import com.elife.common.core.controller.BaseController;
import com.elife.common.core.domain.AjaxResult;
import com.elife.common.enums.BusinessType;
import com.elife.feature.domain.ShopItem;
import com.elife.feature.service.IShopItemService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 商品信息Controller
 *
 * @author ishgrina
 * @date 2023-05-15
 */
@RestController
@RequestMapping("/feature/shopItem")
public class ShopItemController extends BaseController
{
    @Autowired
    private IShopItemService shopItemService;

    /**
     * 查询商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('feature:shopItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopItem shopItem)
    {
        startPage();
        List<ShopItem> list = shopItemService.selectShopItemList(shopItem);
        return getDataTable(list);
    }

    /**
     * 导出商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('feature:shopItem:export')")
    @Log(title = "商品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopItem shopItem)
    {
        List<ShopItem> list = shopItemService.selectShopItemList(shopItem);
        ExcelUtil<ShopItem> util = new ExcelUtil<ShopItem>(ShopItem.class);
        util.exportExcel(response, list, "商品信息数据");
    }

    /**
     * 获取商品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('feature:shopItem:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return AjaxResult.success(shopItemService.selectShopItemByRecordId(recordId));
    }

    /**
     * 新增商品信息
     */
    @PreAuthorize("@ss.hasPermi('feature:shopItem:add')")
    @Log(title = "商品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopItem shopItem)
    {
        return toAjax(shopItemService.insertShopItem(shopItem));
    }

    /**
     * 修改商品信息
     */
    @PreAuthorize("@ss.hasPermi('feature:shopItem:edit')")
    @Log(title = "商品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopItem shopItem)
    {
        return toAjax(shopItemService.updateShopItem(shopItem));
    }

    /**
     * 删除商品信息
     */
    @PreAuthorize("@ss.hasPermi('feature:shopItem:remove')")
    @Log(title = "商品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(shopItemService.deleteShopItemByRecordIds(recordIds));
    }
}
