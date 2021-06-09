package com.wang.statistics.client;

import com.wang.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //统计某一天的注册人数
    @GetMapping(value = "/ucenter/member/countregister/{day}")
    public R registerCount(@PathVariable String day);
}
