package com.elife.web.controller.ticket;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.elife.common.exception.ServiceException;
import com.elife.feature.domain.Company;
import com.elife.feature.service.ICompanyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.elife.common.annotation.Log;
import com.elife.common.core.controller.BaseController;
import com.elife.common.core.domain.AjaxResult;
import com.elife.common.enums.BusinessType;
import com.elife.ticket.domain.Ticket;
import com.elife.ticket.service.ITicketService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 服务单Controller
 *
 * @author isghrina
 * @date 2023-04-18
 */
@RestController
@RequestMapping("/feature/ticket")
public class TicketController extends BaseController {
    @Autowired
    private ITicketService ticketService;

    @Autowired
    private ICompanyService companyService;

    /**
     * 查询服务单列表
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:list')")
    @GetMapping("/list")
    public TableDataInfo list(Ticket ticket) {
        startPage();
        List<Ticket> list = ticketService.selectTicketList(ticket);
        return getDataTable(list);
    }

    /**
     * 导出服务单列表
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:export')")
    @Log(title = "服务单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Ticket ticket) {
        List<Ticket> list = ticketService.selectTicketList(ticket);
        ExcelUtil<Ticket> util = new ExcelUtil<Ticket>(Ticket.class);
        util.exportExcel(response, list, "服务单数据");
    }

    /**
     * 获取服务单详细信息
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:query')")
    @GetMapping(value = "/detail/{ticketId}")
    public AjaxResult getInfo(@PathVariable("ticketId") Long ticketId) {
        return AjaxResult.success(ticketService.selectTicketByTicketId(ticketId));
    }

    /**
     * 获取父单列表
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:list')")
    @GetMapping(value = "/parent/{ticketId}")
    public TableDataInfo listChildTicket(@PathVariable("ticketId") Long ticketId) {
        Ticket target = new Ticket();
        target.setParentId(ticketId);

        startPage();
        List<Ticket> list = ticketService.selectTicketList(target);
        return getDataTable(list);
    }

    /**
     * 新增服务单
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:add')")
    @Log(title = "服务单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Ticket ticket) {
        return toAjax(ticketService.insertTicket(ticket));
    }

    /**
     * 修改服务单
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:edit')")
    @Log(title = "服务单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Ticket ticket) {
        return toAjax(ticketService.updateTicket(ticket));
    }

    /**
     * 删除服务单
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:remove')")
    @Log(title = "服务单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ticketIds}")
    public AjaxResult remove(@PathVariable Long[] ticketIds) {
        return toAjax(ticketService.deleteTicketByTicketIds(ticketIds));
    }

    /**
     * 申请服务单
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:add')")
    @Log(title = "服务单", businessType = BusinessType.INSERT)
    @PostMapping("/apply")
    public AjaxResult apply(@RequestBody Ticket ticket) {
        return toAjax(ticketService.applyTicket(ticket));
//        try{
//            if(null == ticket.getCompanyId()){
//                return error("失败，公司ID不存在。 如果持续出现此错误，请联系系统管理员");
//            }
//            Company company = companyService.selectCompanyByCompanyId(ticket.getCompanyId());
//        }
//        catch (ServiceException e){
//            e.printStackTrace();
//            return error(e.getMessage());
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return error("出现未知错误，请联系系统管理员");
//        }
    }

}
