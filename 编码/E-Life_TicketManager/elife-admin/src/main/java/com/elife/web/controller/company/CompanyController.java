package com.elife.web.controller.company;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.elife.common.core.domain.entity.SysUser;
import com.elife.common.utils.DateUtils;
import com.elife.system.service.ISysUserService;
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
import com.elife.feature.domain.Company;
import com.elife.feature.service.ICompanyService;
import com.elife.common.utils.poi.ExcelUtil;
import com.elife.common.core.page.TableDataInfo;

/**
 * 公司管理Controller
 *
 * @author ishgrina
 * @date 2023-04-18
 */
@RestController
@RequestMapping("/feature/company")
public class CompanyController extends BaseController {
    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询公司管理列表
     */
    @PreAuthorize("@ss.hasAnyPermi('feature:company:list,feature:company:detail')")
    @GetMapping("/list")
    public TableDataInfo list(Company company) {
        startPage();
        List<Company> list = companyService.selectCompanyList(company);
        return getDataTable(list);
    }

    /**
     * 查询公司员工列表
     */
    @PreAuthorize("@ss.hasAnyPermi('feature:company:list,feature:company:detail')")
    @GetMapping("/list/employee/{companyId}")
    public TableDataInfo list(@PathVariable Long companyId) {
        List<SysUser> list = userService.selectUserByCompany(companyId);
        return getDataTable(list);
    }

    /**
     * 导出公司管理列表
     */
    @PreAuthorize("@ss.hasPermi('feature:company:export')")
    @Log(title = "公司管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Company company) {
        List<Company> list = companyService.selectCompanyList(company);
        ExcelUtil<Company> util = new ExcelUtil<Company>(Company.class);
        util.exportExcel(response, list, "公司管理数据");
    }

    /**
     * 获取公司管理详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('feature:company:query,feature:company:detail')")
    @GetMapping(value = "/detail/{companyId}")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId) {
        return AjaxResult.success(companyService.selectCompanyByCompanyId(companyId));
    }

    /**
     * 根据公司所有人获取公司信息
     */
    @PreAuthorize("@ss.hasAnyPermi('feature:company:list,feature:company:detail')")
    @GetMapping(value = "/owner/{userId}")
    public AjaxResult getCompanyByOwner(@PathVariable("userId") Long userId) {
        Company query = new Company();
        query.setCompanyMaster(userId);
        List<Company> resList = companyService.selectCompanyList(query);
        if (resList.size() > 0) {
            return AjaxResult.success(resList.get(0));
        } else {
            return AjaxResult.success();
        }
    }

    /**
     * 新增公司管理
     */
    @PreAuthorize("@ss.hasPermi('feature:company:add')")
    @Log(title = "公司管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Company company) {
        // 初始化公司数据
        // 公司所有者为当前登录用户
        company.setCompanyMaster(getLoginUser().getUserId());
        // 公司申请提交日期为当前日期
        company.setApplyDate(DateUtils.getNowDate());
        // 初始状态为等待审批
        company.setStatus(0);
        return toAjax(companyService.insertCompany(company));
    }

    /**
     * 修改公司管理
     */
    @PreAuthorize("@ss.hasPermi('feature:company:edit')")
    @Log(title = "公司管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Company company) {
        return toAjax(companyService.updateCompany(company));
    }

    /**
     * 删除公司管理
     */
    @PreAuthorize("@ss.hasPermi('feature:company:remove')")
    @Log(title = "公司管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{companyIds}")
    public AjaxResult remove(@PathVariable Long[] companyIds) {
        return toAjax(companyService.deleteCompanyByCompanyIds(companyIds));
    }


    @PreAuthorize("@ss.hasPermi('feature:company:remove')")
    @PostMapping("/settlement/{companyId}")
    public AjaxResult settlement(@PathVariable Long companyId) {
        return toAjax(companyService.companySettlement(companyId));
    }


}
