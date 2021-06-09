package com.wang.order.service;

import com.wang.order.pojo.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author WangMengcheng
 * @since 2021-01-04
 */
public interface PayLogService extends IService<PayLog> {

    //生成支付二维码
    Map createNative(String orderNo);

    //查询支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //修改订单状态
    void updateOrderStatus(Map<String, String> map);
}
