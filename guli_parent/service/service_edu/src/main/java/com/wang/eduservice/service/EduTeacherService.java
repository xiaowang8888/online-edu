package com.wang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.eduservice.pojo.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-18
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //分页查询讲师
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
