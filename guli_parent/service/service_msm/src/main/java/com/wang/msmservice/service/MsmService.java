package com.wang.msmservice.service;

import java.util.Map;

public interface MsmService {

    //发送短信
    boolean sendMessage(Map<String,Object> param, String phoneNumber);
}
