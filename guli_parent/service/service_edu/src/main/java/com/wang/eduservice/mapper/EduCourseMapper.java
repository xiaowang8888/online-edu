package com.wang.eduservice.mapper;

import com.wang.eduservice.pojo.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.eduservice.pojo.vo.CourseInfoFrontVo;
import com.wang.eduservice.pojo.vo.CoursePublishVo;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //查询将要发布的课程信息
    CoursePublishVo selectCoursePublish(String id);

    //根据课程id查询，编写sql语句查询课程信息(前台详情页)
    CourseInfoFrontVo getFrontCourseInfo(String id);
}
