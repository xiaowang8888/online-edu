package com.wang.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutils.JwtUtils;
import com.wang.commonutils.R;
import com.wang.order.pojo.Order;
import com.wang.order.service.OrderService;
import com.wang.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author WangMengcheng
 * @since 2021-01-04
 */
@RestController
@Api(description = "订单接口")
// @CrossOrigin
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //根据课程id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.saveOrder(courseId, memberId);
        return R.ok().data("orderId", orderId);
    }

    //根据订单号获取订单
    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    //根据用户id和课程id查询订单信息判断用户是否购买课程
    @GetMapping("isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable String memberid, @PathVariable String id) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<Order>().eq("member_id", memberid).eq("course_id", id).eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }

}

