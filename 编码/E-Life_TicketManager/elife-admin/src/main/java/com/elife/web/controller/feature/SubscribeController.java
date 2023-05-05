package com.elife.web.controller.feature;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.elife.feature.service.ISubscribeService;
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
import com.elife.feature.domain.Subscribe;
import com.elife.feature.service.ICompanyService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 订阅Controller
 *
 * @author Ishgrina
 * @date 2023-04-21
 */
@RestController
@RequestMapping("/feature/subscribe")
public class SubscribeController extends BaseController
{
    @Autowired
    private ISubscribeService subscribeService;

    /**
     * 查询订阅列表
     */
    @PreAuthorize("@ss.hasPermi('feature:company:detail')")
    @GetMapping("/list")
    public TableDataInfo list(@RequestBody(required = false) Subscribe subscribe)
    {
        startPage();
        List<Subscribe> list = subscribeService.selectSubscribeList(subscribe);
        return getDataTable(list);
    }

//    /**
//     * 获取订阅详细信息
//     */
//    @PreAuthorize("@ss.hasPermi('feature:subscribe:query')")
//    @GetMapping(value = "/{userId}")
//    public AjaxResult getInfo(@PathVariable("userId") Long userId)
//    {
//        return AjaxResult.success(subscribeService.selectSubscribeByUserId(userId));
//    }

    /**
     * 新增订阅
     */
    @PreAuthorize("@ss.hasPermi('feature:subscribe:add')")
    @Log(title = "订阅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Subscribe subscribe)
    {
        return toAjax(subscribeService.insertSubscribe(subscribe));
    }

//    /**
//     * 修改订阅
//     */
//    @PreAuthorize("@ss.hasPermi('feature:subscribe:edit')")
//    @Log(title = "订阅", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody Subscribe subscribe)
//    {
//        return toAjax(subscribeService.updateSubscribe(subscribe));
//    }

//    /**
//     * 删除订阅
//     */
//    @PreAuthorize("@ss.hasPermi('feature:subscribe:remove')")
//    @Log(title = "订阅", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{companyId}")
//    public AjaxResult remove(@PathVariable Long companyId)
//    {
//        return toAjax(subscribeService.unsubscribeByCompanyId(companyId));
//    }

    @PreAuthorize("@ss.hasPermi('feature:subscribe:unsubscribe')")
    @Log(title = "取消订阅", businessType = BusinessType.DELETE)
    @DeleteMapping("/unsubscribe/{companyId}")
    public AjaxResult unsubscribe(@PathVariable Long companyId){
        return toAjax(subscribeService.unsubscribeByCompanyId(companyId));
    }

    @PreAuthorize("@ss.hasPermi('feature:subscribe:subscribe')")
    @Log(title="公司订阅", businessType = BusinessType.INSERT)
    @PostMapping("/subscribe/{companyId}")
    public AjaxResult subscribe(@PathVariable Long companyId){
        return toAjax(subscribeService.subscribeByCompanyId(companyId));
    }
}
