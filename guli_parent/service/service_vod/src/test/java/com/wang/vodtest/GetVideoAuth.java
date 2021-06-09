package com.wang.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

public class GetVideoAuth {
    public static void main(String[] args) throws Exception{
        //1.创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GB4TDNsQaMGAQjE2MU5", "kpETOyXl01mmQl1mWOX8Mr8VuYxD2L");

        //2.创建获取视频凭证的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //3.向request中设置视频id值
        request.setVideoId("c0a62ae709d548bb8c625357748de987");

        //4.调用初始化对象中的方法传递request，获取凭证
        response = client.getAcsResponse(request);
        System.out.println("视频凭证："+response.getPlayAuth());
    }
}
