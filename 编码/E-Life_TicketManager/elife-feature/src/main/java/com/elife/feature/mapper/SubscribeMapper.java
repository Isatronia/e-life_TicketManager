package com.elife.feature.mapper;

import java.util.List;
import com.elife.feature.domain.Subscribe;
import com.elife.feature.domain.Company;

/**
 * 订阅Mapper接口
 *
 * @author Ishgrina
 * @date 2023-04-20
 */
public interface SubscribeMapper
{
    /**
     * 查询订阅
     *
     * @param userId 订阅主键
     * @return 订阅
     */
    public Subscribe selectSubscribeByUserId(Long userId);

    /**
     * 查询订阅列表
     *
     * @param subscribe 订阅
     * @return 订阅集合
     */
    public List<Subscribe> selectSubscribeList(Subscribe subscribe);

    /**
     * 新增订阅
     *
     * @param subscribe 订阅
     * @return 结果
     */
    public int insertSubscribe(Subscribe subscribe);


    /**
     * 取消订阅
     *
     * @param subscribe 订阅
     * @return 结果
     */
    public int unsubscribe(Subscribe subscribe);

    /**
     * 修改订阅
     *
     * @param subscribe 订阅
     * @return 结果
     */
    public int updateSubscribe(Subscribe subscribe);

    /**
     * 删除订阅
     *
     * @param subscribe 订阅信息
     * @return 结果
     */
    public int deleteSubscribe(Subscribe subscribe);
//
//    /**
//     * 批量删除订阅
//     *
//     * @param userIds 需要删除的数据主键集合
//     * @return 结果
//     */
//    public int deleteSubscribeByUserIds(Long[] userIds);
//
//    /**
//     * 批量删除公司管理
//     *
//     * @param userIds 需要删除的数据主键集合
//     * @return 结果
//     */
//    public int deleteCompanyByCompanyIds(Long[] userIds);
//
//    /**
//     * 批量新增公司管理
//     *
//     * @param companyList 公司管理列表
//     * @return 结果
//     */
//    public int batchCompany(List<Company> companyList);
//
//
//    /**
//     * 通过订阅主键删除公司管理信息
//     *
//     * @param userId 订阅ID
//     * @return 结果
//     */
//    public int deleteCompanyByCompanyId(Long userId);
}
