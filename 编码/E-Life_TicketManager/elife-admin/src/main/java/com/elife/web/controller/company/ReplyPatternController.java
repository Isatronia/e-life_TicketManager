package com.elife.web.controller.company;

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
import com.elife.feature.domain.ReplyPattern;
import com.elife.feature.service.IReplyPatternService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 回复模板管理Controller
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
@RestController
@RequestMapping("/feature/pattern")
public class ReplyPatternController extends BaseController
{
    @Autowired
    private IReplyPatternService replyPatternService;

    /**
     * 查询回复模板管理列表
     */
    @PreAuthorize("@ss.hasPermi('feature:pattern:list')")
    @GetMapping("/list")
    public TableDataInfo list(ReplyPattern replyPattern)
    {
        startPage();
        List<ReplyPattern> list = replyPatternService.selectReplyPatternList(replyPattern);
        return getDataTable(list);
    }

    /**
     * 导出回复模板管理列表
     */
    @PreAuthorize("@ss.hasPermi('feature:pattern:export')")
    @Log(title = "回复模板管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReplyPattern replyPattern)
    {
        List<ReplyPattern> list = replyPatternService.selectReplyPatternList(replyPattern);
        ExcelUtil<ReplyPattern> util = new ExcelUtil<ReplyPattern>(ReplyPattern.class);
        util.exportExcel(response, list, "回复模板管理数据");
    }

    /**
     * 获取回复模板管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('feature:pattern:query')")
    @GetMapping(value = "/{patternId}")
    public AjaxResult getInfo(@PathVariable("patternId") Long patternId)
    {
        return AjaxResult.success(replyPatternService.selectReplyPatternByPatternId(patternId));
    }

    /**
     * 新增回复模板管理
     */
    @PreAuthorize("@ss.hasPermi('feature:pattern:add')")
    @Log(title = "回复模板管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReplyPattern replyPattern)
    {
        return toAjax(replyPatternService.insertReplyPattern(replyPattern));
    }

    /**
     * 修改回复模板管理
     */
    @PreAuthorize("@ss.hasPermi('feature:pattern:edit')")
    @Log(title = "回复模板管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReplyPattern replyPattern)
    {
        return toAjax(replyPatternService.updateReplyPattern(replyPattern));
    }

    /**
     * 删除回复模板管理
     */
    @PreAuthorize("@ss.hasPermi('feature:pattern:remove')")
    @Log(title = "回复模板管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{patternIds}")
    public AjaxResult remove(@PathVariable Long[] patternIds)
    {
        return toAjax(replyPatternService.deleteReplyPatternByPatternIds(patternIds));
    }
}
