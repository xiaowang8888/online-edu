package com.wang.ucenter.mapper;

import com.wang.ucenter.pojo.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-31
 */
public interface MemberMapper extends BaseMapper<Member> {

    //统计某一天的注册人数
    Integer selectRegisterCount(String day);
}
