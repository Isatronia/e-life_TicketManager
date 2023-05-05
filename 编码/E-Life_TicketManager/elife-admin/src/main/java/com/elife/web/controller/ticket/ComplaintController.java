package com.elife.web.controller.ticket;

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
import com.elife.ticket.domain.Complaint;
import com.elife.ticket.service.IComplaintService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 投诉Controller
 * 
 * @author ishgrina
 * @date 2023-04-18
 */
@RestController
@RequestMapping("/feature/complaint")
public class ComplaintController extends BaseController
{
    @Autowired
    private IComplaintService complaintService;

    /**
     * 查询投诉列表
     */
    @PreAuthorize("@ss.hasPermi('feature:complaint:list')")
    @GetMapping("/list")
    public TableDataInfo list(Complaint complaint)
    {
        startPage();
        List<Complaint> list = complaintService.selectComplaintList(complaint);
        return getDataTable(list);
    }

    /**
     * 导出投诉列表
     */
    @PreAuthorize("@ss.hasPermi('feature:complaint:export')")
    @Log(title = "投诉", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Complaint complaint)
    {
        List<Complaint> list = complaintService.selectComplaintList(complaint);
        ExcelUtil<Complaint> util = new ExcelUtil<Complaint>(Complaint.class);
        util.exportExcel(response, list, "投诉数据");
    }

    /**
     * 获取投诉详细信息
     */
    @PreAuthorize("@ss.hasPermi('feature:complaint:query')")
    @GetMapping(value = "/{complaintId}")
    public AjaxResult getInfo(@PathVariable("complaintId") Long complaintId)
    {
        return AjaxResult.success(complaintService.selectComplaintByComplaintId(complaintId));
    }

    /**
     * 新增投诉
     */
    @PreAuthorize("@ss.hasPermi('feature:complaint:add')")
    @Log(title = "投诉", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Complaint complaint)
    {
        return toAjax(complaintService.insertComplaint(complaint));
    }

    /**
     * 修改投诉
     */
    @PreAuthorize("@ss.hasPermi('feature:complaint:edit')")
    @Log(title = "投诉", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Complaint complaint)
    {
        return toAjax(complaintService.updateComplaint(complaint));
    }

    /**
     * 删除投诉
     */
    @PreAuthorize("@ss.hasPermi('feature:complaint:remove')")
    @Log(title = "投诉", businessType = BusinessType.DELETE)
	@DeleteMapping("/{complaintIds}")
    public AjaxResult remove(@PathVariable Long[] complaintIds)
    {
        return toAjax(complaintService.deleteComplaintByComplaintIds(complaintIds));
    }
}
