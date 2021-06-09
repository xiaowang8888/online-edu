package com.wang.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutils.JwtUtils;
import com.wang.commonutils.MD5;
import com.wang.servicebase.exceptionhandler.GuliException;
import com.wang.ucenter.pojo.Member;
import com.wang.ucenter.mapper.MemberMapper;
import com.wang.ucenter.pojo.RegisterVo;
import com.wang.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-31
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //登录
    @Override
    public String login(Member member) {
        String mobile=member.getMobile();
        String password=member.getPassword();

        //如果手机号或密码一个为空则直接抛异常
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }

        //判断手机号是否可以查询到
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member queryMember = baseMapper.selectOne(wrapper);

        //判断密码是否正确
        if(!MD5.encrypt(password).equals(queryMember.getPassword())){
            throw new GuliException(20001,"登录失败");
        }

        //判断用户是否废弃
        if(queryMember.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }

        //使用JWT生成token字符串
        String token= JwtUtils.getJwtToken(queryMember.getId(),queryMember.getNickname());
        return token;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"注册失败");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobleCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobleCode)) {
            throw new GuliException(20001,"注册失败");
        }

        //判断手机号是否重复，表里面存在相同的手机号则不能重复添加
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001,"注册失败 ");
        }

        //添加注册信息到数据库
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://guli-edu-avator1.oss-cn-shenzhen.aliyuncs.com/2020/12/22/7385203e97af45f0a6acfca401355551file.png");
        baseMapper.insert(member);
    }

    //根据微信openid查询记录
    @Override
    public Member getByOpenid(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }

    //统计某一天的注册人数
    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
