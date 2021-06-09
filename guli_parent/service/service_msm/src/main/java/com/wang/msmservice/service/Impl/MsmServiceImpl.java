package com.wang.msmservice.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wang.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    //发送短信
    @Override
    public boolean sendMessage(Map<String, Object> param, String phoneNumber) {
        if(StringUtils.isEmpty(phoneNumber)) return false;

        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4GB4TDNsQaMGAQjE2MU5", "kpETOyXl01mmQl1mWOX8Mr8VuYxD2L");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关固定的参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phoneNumber);                         //手机号
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");                //签名
        request.putQueryParameter("TemplateCode", "SMS_208640063");               //模板号
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));     //验证码数据，需转换成JSON数据

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
