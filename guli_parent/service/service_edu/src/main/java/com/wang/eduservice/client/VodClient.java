package com.wang.eduservice.client;

import com.wang.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
// @FeignClient("service-vod")
@Component
public interface VodClient {
    //根据视频id删除阿里云上的视频
    @DeleteMapping("/eduvod/video/deleteVideo/{id}")
    public R deleteVideo(@PathVariable("id") String id);

    //批量删除阿里云视频
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteVideoBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
