package com.elife.web.controller.feature;

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
import com.elife.feature.domain.Block;
import com.elife.feature.service.IBlockService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 黑名单Controller
 *
 * @author ishgrina
 * @date 2023-05-05
 */
@RestController
@RequestMapping("/feature/block")
public class BlockController extends BaseController
{
    @Autowired
    private IBlockService blockService;

    /**
     * 查询黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('feature:block:list')")
    @GetMapping("/list")
    public TableDataInfo list(Block block)
    {
        startPage();
        List<Block> list = blockService.selectBlockList(block);
        return getDataTable(list);
    }

    /**
     * 导出黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('feature:block:export')")
    @Log(title = "黑名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Block block)
    {
        List<Block> list = blockService.selectBlockList(block);
        ExcelUtil<Block> util = new ExcelUtil<Block>(Block.class);
        util.exportExcel(response, list, "黑名单数据");
    }

    /**
     * 获取黑名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('feature:block:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return AjaxResult.success(blockService.selectBlockByUserId(userId));
    }

    /**
     * 新增黑名单
     */
    @PreAuthorize("@ss.hasPermi('feature:block:add')")
    @Log(title = "黑名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Block block)
    {
        return toAjax(blockService.insertBlock(block));
    }

    /**
     * 修改黑名单
     */
    @PreAuthorize("@ss.hasPermi('feature:block:edit')")
    @Log(title = "黑名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Block block)
    {
        return toAjax(blockService.updateBlock(block));
    }

    /**
     * 删除黑名单
     */
    @PreAuthorize("@ss.hasPermi('feature:block:remove')")
    @Log(title = "黑名单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(blockService.deleteBlockByUserIds(userIds));
    }
}
