package com.wang.eduservice.client;

import com.wang.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 服务调用出错才会执行此类的方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频失败！");
    }

    @Override
    public R deleteVideoBatch(List<String> videoIdList) {
        return R.error().message("批量删除视频失败！");
    }
}
