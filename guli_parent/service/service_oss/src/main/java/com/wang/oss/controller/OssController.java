package com.wang.oss.controller;

import com.wang.commonutils.R;
import com.wang.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// @CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 1.上传头像的方法
     */
    @PostMapping()
    public R upload(MultipartFile file){    //获取上传文件
        //返回头像文件的url
        String url = ossService.uploadFileAvator(file);
        return R.ok().data("url",url);
    }
}
