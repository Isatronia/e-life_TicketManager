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
import com.elife.feature.domain.TicketType;
import com.elife.feature.service.ITicketTypeService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 问题类型Controller
 *
 * @author ishgrina
 * @date 2023-04-18
 */
@RestController
@RequestMapping("/feature/ticketType")
public class TicketTypeController extends BaseController {
    @Autowired
    private ITicketTypeService ticketTypeService;

    /**
     * 查询问题类型列表
     */
    @PreAuthorize("@ss.hasPermi('feature:ticketType:list')")
    @GetMapping("/list")
    public TableDataInfo list(TicketType ticketType) {
        startPage();
        List<TicketType> list = ticketTypeService.selectTicketTypeList(ticketType);
        return getDataTable(list);
    }

    /**
     * 导出问题类型列表
     */
    @PreAuthorize("@ss.hasPermi('feature:ticketType:export')")
    @Log(title = "问题类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TicketType ticketType) {
        List<TicketType> list = ticketTypeService.selectTicketTypeList(ticketType);
        ExcelUtil<TicketType> util = new ExcelUtil<TicketType>(TicketType.class);
        util.exportExcel(response, list, "问题类型数据");
    }

    /**
     * 获取问题类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('feature:ticketType:query')")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId) {
        return AjaxResult.success(ticketTypeService.selectTicketTypeByTypeId(typeId));
    }

    /**
     * 新增问题类型
     */
    @PreAuthorize("@ss.hasPermi('feature:ticketType:add')")
    @Log(title = "问题类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TicketType ticketType) {
        return toAjax(ticketTypeService.insertTicketType(ticketType));
    }

    /**
     * 修改问题类型
     */
    @PreAuthorize("@ss.hasPermi('feature:ticketType:edit')")
    @Log(title = "问题类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TicketType ticketType) {
        return toAjax(ticketTypeService.updateTicketType(ticketType));
    }

    /**
     * 删除问题类型
     */
    @PreAuthorize("@ss.hasPermi('feature:ticketType:remove')")
    @Log(title = "问题类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds) {
        return toAjax(ticketTypeService.deleteTicketTypeByTypeIds(typeIds));
    }
}
