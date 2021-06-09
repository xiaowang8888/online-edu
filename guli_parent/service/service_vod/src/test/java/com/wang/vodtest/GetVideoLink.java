package com.wang.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

public class GetVideoLink {
    public static void main(String[] args) throws Exception{
        //1.创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GB4TDNsQaMGAQjE2MU5", "kpETOyXl01mmQl1mWOX8Mr8VuYxD2L");
        //2.创建获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //3.向request中设置视频id值
        request.setVideoId("c0a62ae709d548bb8c625357748de987");

        //4.调用初始化对象中的方法传递request，获取数据
        response  = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

}
