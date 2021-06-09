package com.wang.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface VodService {
    //上传视频到阿里云
    String uploadVideo(MultipartFile file);

    //批量删除多个阿里云上的视频
    void deleteVideoList(List<String> videoIdList);
}
