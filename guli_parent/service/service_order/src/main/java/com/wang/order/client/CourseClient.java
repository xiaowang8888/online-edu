package com.wang.order.client;

import com.wang.commonutils.CourseInfoFrontVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface CourseClient {

    //根据课程id查询课程基本信息
    @GetMapping("/eduservice/courseFront/getDto/{id}")
    public CourseInfoFrontVo getDto(@PathVariable String id);
}
