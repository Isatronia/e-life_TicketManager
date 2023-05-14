package com.elife.feature.service;

import java.util.List;
import com.elife.feature.domain.Subscribe;

/**
 * 订阅Service接口
 *
 * @author Ishgrina
 * @date 2023-04-21
 */
public interface ISubscribeService
{
    /**
     * 订阅公司信息
     *
     * @param companyId 公司主键
     * @return 结果
     */
    public int subscribeByCompanyId(Long companyId);

    /**
     * 取消订阅公司
     *
     * @param companyId 公司主键
     * @return 结果
     */
    public int unsubscribeByCompanyId(Long companyId);

    /**
     * 订阅公司列表
     *
     * @param subscribe 订阅关系列表
     * @return 结果
     */
    public List<Subscribe> selectSubscribeList(Subscribe subscribe);

    /**
     * 调试功能: 强制添加订阅关系
     *
     * @param subscribe 订阅信息
     * @return 结果
     */
    public int insertSubscribe(Subscribe subscribe);

    /**
     * 删除订阅信息
     *
     * @param subscribe 订阅信息
     * @return 结果
     */
    public int deleteSubscribeByUserId(Subscribe subscribe);

    /**
     * 更新订阅信息状态
     *
     * @param subscribe 订阅信息
     * @return 结果
     */
    public int updateSubscribeStatus(Subscribe subscribe);

    /**
     * 强制取消订阅
     *
     * @param subscribe 订阅信息
     * @return 结果
     */
    public int forceUnsubscribe(Subscribe subscribe);

    /**
     * 同意公司的邀请
     *
     * @param subscribe
     * @return
     */
    public int accept(Subscribe subscribe);
}