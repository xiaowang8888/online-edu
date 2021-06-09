package com.wang.msmservice.controller;

import com.wang.commonutils.R;
import com.wang.msmservice.service.MsmService;
import com.wang.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
// @CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送短信
    @GetMapping("sendMessage/{phoneNumber}")
    public R sendMessage(@PathVariable String phoneNumber){
        //从redis中读取获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //如果redis获取不到进行阿里云发送
        //生成随机值，传递给阿里云进行转发
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param=new HashMap<>();
        param.put("code",code);
        boolean b = msmService.sendMessage(param, phoneNumber);
        if(b){
            //发送成功，把验证码放到redis中并设置有效时间
            redisTemplate.opsForValue().set(phoneNumber,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败！");
        }
    }
}
