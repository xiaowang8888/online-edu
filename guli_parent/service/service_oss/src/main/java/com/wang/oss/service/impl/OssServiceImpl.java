package com.wang.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wang.oss.service.OssService;
import com.wang.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    //上传头像到OSS
    @Override
    public String uploadFileAvator(MultipartFile file) {
        //通过工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // System.out.println(bucketName);

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传的输入流。
            InputStream inputStream =file.getInputStream();
            //获取上传文件的文件名
            String filename = file.getOriginalFilename();

            //文件重名后会发生覆盖，解决方法：给每个文件名加一个随机的数字
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename=uuid+filename;

            //按日期进行文件分类存储，类似2020/12/22/1.jpg
            //获取当前日期
            String date = new DateTime().toString("yyyy/MM/dd");
            filename=date+"/"+filename;
            /**
             * 1.第一个参数bucketName
             * 2.第二个参数 文件上传到OSS的路径和文件名  /aa/bb/1.jpg
             * 3.文件输入流
             */
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //返回上传文件的URL
            String url="https://"+bucketName+"."+endpoint+"/"+filename;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
