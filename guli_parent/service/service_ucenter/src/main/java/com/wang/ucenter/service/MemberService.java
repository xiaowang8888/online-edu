package com.wang.ucenter.service;

import com.wang.ucenter.pojo.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.ucenter.pojo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-31
 */
public interface MemberService extends IService<Member> {

    //登录
    String login(Member member);

    //注册
    void register(RegisterVo registerVo);

    //根据微信openid查询记录
    Member getByOpenid(String openid);

    //统计某一天的注册人数
    Integer countRegisterByDay(String day);
}
