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
import com.elife.common.domain.Message;
import com.elife.common.service.IMessageService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 消息查询Controller
 *
 * @author ishgrina
 * @date 2023-04-18
 */
@RestController
@RequestMapping("/monitor/message")
public class MessageController extends BaseController {
    @Autowired
    private IMessageService messageService;

    /**
     * 查询消息查询列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(Message message) {
        startPage();
        List<Message> list = messageService.selectMessageList(message);
        return getDataTable(list);
    }

    /**
     * 导出消息查询列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:export')")
    @Log(title = "消息查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Message message) {
        List<Message> list = messageService.selectMessageList(message);
        ExcelUtil<Message> util = new ExcelUtil<Message>(Message.class);
        util.exportExcel(response, list, "消息查询数据");
    }

    /**
     * 获取消息查询详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:query')")
    @GetMapping(value = "/detail/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId) {
        return AjaxResult.success(messageService.selectMessageByMessageId(messageId));
    }

    /**
     * 根据服务单获取消息列表
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:list')")
    @GetMapping(value = "/ticket/{ticketId}")
    public TableDataInfo getMessageByTicket(@PathVariable("ticketId") Long ticketId) {
        startPage();
        List<Message> list = messageService.selectMessageListByTicketId(ticketId);
        return getDataTable(list);
    }

    /**
     * 根据用户获取私信（通知）列表
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:list')")
    @GetMapping(value = "/dm/{ticketId}")
    public TableDataInfo getDirectMessage(@PathVariable(value = "userId", required = false) Long userId) {
        startPage();
        if (userId == null) userId = getLoginUser().getUserId();
        List<Message> list = messageService.selectMessageListByTicketId(userId);
        return getDataTable(list);
    }

    /**
     * 发送消息
     */
    @PreAuthorize("@ss.hasPermi('feature:ticket:list')")
    @PostMapping("/send")
    public AjaxResult sendMessage(@RequestBody Message message) {
        System.out.println(message);
        message.setAuthor(getLoginUser().getUserId());
        message.setMessageType(0L);
        message.setCreateBy(getLoginUser().getUsername());
        return AjaxResult.success(messageService.insertMessage(message));
    }

    /**
     * 新增消息查询
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:add')")
    @Log(title = "消息查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Message message) {
        return toAjax(messageService.insertMessage(message));
    }

    /**
     * 修改消息查询
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:edit')")
    @Log(title = "消息查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Message message) {
        return toAjax(messageService.updateMessage(message));
    }

    /**
     * 删除消息查询
     */
    @PreAuthorize("@ss.hasPermi('monitor:message:remove')")
    @Log(title = "消息查询", businessType = BusinessType.DELETE)
    @DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds) {
        return toAjax(messageService.deleteMessageByMessageIds(messageIds));
    }
}
