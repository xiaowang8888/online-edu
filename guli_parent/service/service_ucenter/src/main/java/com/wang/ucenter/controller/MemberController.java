package com.wang.ucenter.controller;


import com.wang.commonutils.JwtUtils;
import com.wang.commonutils.R;
import com.wang.ucenter.pojo.Member;
import com.wang.ucenter.pojo.RegisterVo;
import com.wang.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-31
 */
@RestController
// @CrossOrigin
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("login")
    public R login(@RequestBody Member member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据id查询数据库中的用户信息
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    //根据用户id获取用户信息
    @PostMapping("getInfoUc/{id}")
    public Member getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        Member member = memberService.getById(id);
        return member;
    }

    //统计某一天的注册人数
    @GetMapping(value = "countregister/{day}")
    public R registerCount(@PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }
}

