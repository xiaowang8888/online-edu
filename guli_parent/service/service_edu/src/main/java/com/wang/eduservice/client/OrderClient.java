package com.wang.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@FeignClient(value = "service-order", fallback = OrderClientImpl.class)
@Component
public interface OrderClient {
    //根据用户id和课程id查询订单信息判断用户是否购买课程
    @GetMapping("/eduorder/order/isBuyCourse/{memberid}/{id}")
    public boolean isBuyCourse(@PathVariable String memberid, @PathVariable String id);
}
