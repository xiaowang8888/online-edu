package com.wang.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wang.commonutils.R;
import com.wang.servicebase.exceptionhandler.GuliException;
import com.wang.vod.service.VodService;
import com.wang.vod.utils.ConstantPropertiesUtil;
import com.wang.vod.utils.InitVodObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
// @CrossOrigin
@Api(description="阿里云视频点播微服务")
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @ApiOperation(value = "上传视频到阿里云")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId=vodService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId",videoId);
    }

    //根据视频id删除阿里云上的视频
    @ApiOperation(value = "根据id删除阿里云中的视频")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        try {
            //初始化对象
            DefaultAcsClient client = InitVodObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request中设置id
            request.setVideoIds(id);
            //调用初始化对象中的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败！");
        }
    }

    //批量删除多个阿里云上的视频
    @DeleteMapping("delete-batch")
    @ApiOperation(value = "批量删除阿里云中的视频")
    public R deleteVideoList(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteVideoList(videoIdList);
        return R.ok();
    }

    @GetMapping("getPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable String videoId) throws Exception {
        //1.创建初始化对象
        DefaultAcsClient client = InitVodObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

        //2.创建获取视频凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //3.向request中设置视频id值
        request.setVideoId(videoId);

        //4.调用初始化对象中的方法传递request，获取凭证
        response = client.getAcsResponse(request);

        //5.得到播放凭证
        String playAuth = response.getPlayAuth();

        return R.ok().data("playAuth",playAuth).message("获取播放凭证成功");
    }
}
