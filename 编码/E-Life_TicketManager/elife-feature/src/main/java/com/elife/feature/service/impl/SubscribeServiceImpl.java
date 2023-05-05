package com.elife.feature.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elife.feature.mapper.SubscribeMapper;
import com.elife.feature.domain.Subscribe;
import com.elife.feature.service.ISubscribeService;

import static com.elife.common.utils.SecurityUtils.getLoginUser;

/**
 * 订阅Service业务层处理
 *
 * @author Ishgrina
 * @date 2023-04-21
 */
@Service
public class SubscribeServiceImpl implements ISubscribeService
{
    @Autowired
    private SubscribeMapper subscribeMapper;

    /**
     * 订阅公司
     *
     * @param companyId 公司管理主键
     * @return 结果
     */
    @Override
    public int subscribeByCompanyId(Long companyId) {
        return subscribeMapper.insertSubscribe(new Subscribe(getLoginUser().getUserId() ,companyId));
    }

    /**
     * 取消订阅
     *
     * @param companyId 公司主键
     * @return 结果
     */
    @Override
    public int unsubscribeByCompanyId(Long companyId) {
        return subscribeMapper.unsubscribe(new Subscribe(getLoginUser().getUserId() ,companyId));
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
    public int insertSubscribe(Subscribe subscribe)
    {
        return subscribeMapper.insertSubscribe(subscribe);
    }

    /**
     * 删除订阅信息
     *
     * @param subscribe 订阅主键
     * @return 结果
     */
    @Override
    public int deleteSubscribeByUserId(Subscribe subscribe)
    {
        return subscribeMapper.unsubscribe(subscribe);
    }
}
