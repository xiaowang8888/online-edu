package com.wang.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface OssService {
    //上传头像到OSS
    String uploadFileAvator(MultipartFile file);

}
