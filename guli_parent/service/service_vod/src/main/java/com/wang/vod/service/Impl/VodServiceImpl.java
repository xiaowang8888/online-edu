package com.wang.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.wang.commonutils.R;
import com.wang.servicebase.exceptionhandler.GuliException;
import com.wang.vod.service.VodService;
import com.wang.vod.utils.ConstantPropertiesUtil;
import com.wang.vod.utils.InitVodObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    //上传视频到阿里云
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            String title = filename.substring(0, filename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, filename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = null;
            if (response.isSuccess()) {
                videoId=response.getVideoId();
            }else {
                videoId=response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }

    //批量删除多个阿里云上的视频
    @Override
    public void deleteVideoList(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //将List中的id转化为类似11,22,33...
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            //向request中设置id
            request.setVideoIds(videoIds);
            //调用初始化对象中的方法实现删除
            client.getAcsResponse(request);
        }catch (ClientException e){
            throw new GuliException(20001,"删除视频失败！");
        }
    }
}
