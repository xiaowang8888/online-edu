package com.wang.order.service;

import com.wang.order.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author WangMengcheng
 * @since 2021-01-04
 */
public interface OrderService extends IService<Order> {

    //根据课程id和用户id创建订单，返回订单id
    String saveOrder(String courseId, String memberIdByJwtToken);
}
