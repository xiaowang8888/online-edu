package com.wang.eduservice.service;

import com.wang.eduservice.pojo.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.pojo.vo.CourseInfoFrontVo;
import com.wang.eduservice.pojo.vo.CourseInfoVo;
import com.wang.eduservice.pojo.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-23
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息
    String addCourse(CourseInfoVo courseInfoVo);

    //根据课程Id查询课程基本信息(后台页面)
    CourseInfoVo getCourseInfoById(String courseId);

    //修改课程基本信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //查询将要发布的课程信息
    CoursePublishVo getCoursePublish(String id);

    //根据id删除课程
    boolean deleteCourseById(String courseId);

    //根据课程id查询，编写sql语句查询课程信息(前台详情页)
    CourseInfoFrontVo getFrontCourseInfo(String id);
}
