package com.elife.feature.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.elife.common.core.domain.entity.SysDept;
import com.elife.common.core.domain.entity.SysRole;
import com.elife.common.core.domain.entity.SysUser;
import com.elife.common.domain.Message;
import com.elife.common.exception.ServiceException;
import com.elife.common.mapper.MessageMapper;
import com.elife.common.utils.DateUtils;
import com.elife.feature.domain.Block;
import com.elife.feature.domain.Company;
import com.elife.feature.mapper.BlockMapper;
import com.elife.feature.mapper.CompanyMapper;
import com.elife.system.domain.SysConfig;
import com.elife.system.domain.SysUserRole;
import com.elife.system.mapper.SysDeptMapper;
import com.elife.system.mapper.SysRoleMapper;
import com.elife.system.mapper.SysUserMapper;
import com.elife.system.mapper.SysUserRoleMapper;
import com.elife.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.feature.mapper.SubscribeMapper;
import com.elife.feature.domain.Subscribe;
import com.elife.feature.service.ISubscribeService;
import org.springframework.transaction.annotation.Transactional;

import static com.elife.common.utils.SecurityUtils.getLoginUser;

/**
 * 订阅Service业务层处理
 *
 * @author Ishgrina
 * @date 2023-04-21
 */
@Service
public class SubscribeServiceImpl implements ISubscribeService {
    @Autowired
    private SubscribeMapper subscribeMapper;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 订阅公司
     *
     * @param companyId 公司管理主键
     * @return 结果
     */
    @Override
    public int subscribeByCompanyId(Long companyId) {
        return subscribeMapper.insertSubscribe(new Subscribe(getLoginUser().getUserId(), companyId, 0));
    }

    /**
     * 取消订阅
     *
     * @param companyId 公司主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int unsubscribeByCompanyId(Long companyId) {
        Subscribe params = new Subscribe(getLoginUser().getUserId(), companyId);
        removeCompanyRole(params);
        return subscribeMapper.unsubscribe(params);
    }

    /**
     * 订阅列表
     *
     * @return 结果
     */
    @Override
    public List<Subscribe> selectSubscribeList(Subscribe subscribe) {
        return subscribeMapper.selectSubscribeList(subscribe);
    }

    /**
     * 新增订阅
     *
     * @param subscribe 订阅
     * @return 结果
     */
    @Override
    public int insertSubscribe(Subscribe subscribe) {
        // 检查是否在黑名单
        Block param = new Block();
        param.setTargetId(getLoginUser().getUserId());
        param.setUserId(subscribe.getUserId());
        // 如果在黑名单就不添加记录
        if (blockMapper.selectBlockList(param).size() != 0) {
            return 0;
        } else {
            Company company = companyMapper.selectCompanyByCompanyId(subscribe.getCompanyId());
            String url = "/feature/subscribe/accept/" + subscribe.getCompanyId();
            Message msg = new Message();
            // 填写消息
            msg.setMessageType(1L);
            msg.setAuthor(getLoginUser().getUserId());
            msg.setMessageTicket(subscribe.getUserId());
            msg.setCreateBy(getLoginUser().getUsername());
            msg.setCreateTime(DateUtils.getNowDate());
            msg.setContent("我们诚邀您加入" + company.getCompanyName() + "公司，如果您同意邀请，请点击下方链接：[加入我们](" + url + ")");

            messageMapper.insertMessage(msg);
            return subscribeMapper.insertSubscribe(subscribe);
        }
    }

    /**
     * 删除订阅信息
     *
     * @param subscribe 订阅主键
     * @return 结果
     */
    @Override
    public int deleteSubscribeByUserId(Subscribe subscribe) {
        return subscribeMapper.unsubscribe(subscribe);
    }

    /**
     * 更新订阅信息状态
     *
     * @param subscribe 订阅信息
     * @return 结果
     */
    @Override
    public int updateSubscribeStatus(Subscribe subscribe) {
        if (null == subscribe.getCompanyId() || null == subscribe.getUserId())
            return 0;
        return subscribeMapper.updateSubscribe(subscribe);
    }

    /**
     * 强制取消订阅
     *
     * @param subscribe 订阅信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int forceUnsubscribe(Subscribe subscribe) {
        subscribe = subscribeMapper.selectSubscribeList(subscribe).get(0);
        if (0 != subscribe.getStatus()) {
            removeCompanyRole(subscribe);
        }
        return subscribeMapper.deleteSubscribe(subscribe);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int accept(Subscribe subscribe) {
        // 校验订阅信息是否存在
        List<Subscribe> info = subscribeMapper.selectSubscribeList(subscribe);
        if (info.size() == 0) {
            throw new ServiceException("未收到该公司邀请");
        }
        subscribe = info.get(0);
        if (subscribe.getStatus() != 0) {
            throw new ServiceException("已同意该公司邀请，请勿重复提交");
        }
        grantCompanyRole(subscribe);
        subscribe.setStatus(1);
        return subscribeMapper.updateSubscribe(subscribe);
    }

    /**
     * 赋予公司指定的角色，用于通过公司邀请，成为员工
     *
     * @param subscribe
     * @return 结果
     */
    private int grantCompanyRole(Subscribe subscribe) {
        // 校验是否已经获取员工角色，已有则不进行任何操作，返回并报错
        SysUser targetUser = userMapper.selectUserById(subscribe.getUserId());
        SysRole employeeRole = roleMapper.selectRoleById(Long.parseLong(getConfig("system.role.employee")));

        if (targetUser.getRoles().contains(employeeRole)) {
            throw new ServiceException("用户已是员工，添加失败");
        }

        // 获取目标公司，赋予用户部门信息。
        Company company = companyMapper.selectCompanyByCompanyId(subscribe.getCompanyId());

        // 更改用户所属部门
        targetUser.setDeptId(company.getDeptId());
//        targetUser.setDept(deptMapper.selectDeptById(company.getDeptId()));
        userMapper.updateUser(targetUser);

        // 更新用户角色
        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(employeeRole.getRoleId());
        userRole.setUserId(targetUser.getUserId());
        List<SysUserRole> roles = new ArrayList<SysUserRole>(Arrays.asList(new SysUserRole[]{userRole}));

        userRoleMapper.deleteUserRoleByUserId(targetUser.getUserId());
        userRoleMapper.batchUserRole(roles);
        return 1;
    }

    /**
     * 移除公司指定的角色，用于取消订阅、初始化状态
     *
     * @param subscribe
     * @return
     */
    private int removeCompanyRole(Subscribe subscribe) {
        SysUser targetUser = userMapper.selectUserById(subscribe.getUserId());
        Long deptid = Long.parseLong(getConfig("sys.register.initDept"));
        Long defaultRoleId = Long.parseLong(getConfig("sys.register.initRole"));
        if (deptid == null || deptid == 0) {
            throw new ServiceException("未指定初始部门ID!");
        }
        if (defaultRoleId == null || defaultRoleId == 0) {
            throw new ServiceException("未指定初始角色ID!");
        }
        // 删除订阅关系时重置用户角色与部门
        targetUser.setDept(deptMapper.selectDeptById(deptid));
        targetUser.setRoleId(defaultRoleId);
        targetUser.setRoleIds(new Long[]{defaultRoleId});

        userMapper.updateUser(targetUser);
        return 0;
    }

    private String getConfig(String configKey) {
        return configService.selectConfigByKey(configKey);
    }
}
