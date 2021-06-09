package com.wang.order.service.impl;

import com.wang.commonutils.CourseInfoFrontVo;
import com.wang.commonutils.Member;
import com.wang.order.utils.OrderNoUtil;
import com.wang.order.client.CourseClient;
import com.wang.order.client.UcenterClient;
import com.wang.order.pojo.Order;
import com.wang.order.mapper.OrderMapper;
import com.wang.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author WangMengcheng
 * @since 2021-01-04
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private CourseClient courseClient;

    //根据课程id和用户id创建订单，返回订单id
    @Override
    public String saveOrder(String courseId, String memberId) {
        //根据课程id远程调用edu模块中的接口获取课程信息
        CourseInfoFrontVo courseInfoFrontVo = courseClient.getDto(courseId);

        //根据课程id远程调用ucenter模块中的接口获取用户信息
        Member member = ucenterClient.getInfo(memberId);

        //创建订单
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoFrontVo.getTitle());
        order.setCourseCover(courseInfoFrontVo.getCover());
        order.setTeacherName(courseInfoFrontVo.getTeacherName());
        order.setTotalFee(courseInfoFrontVo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
