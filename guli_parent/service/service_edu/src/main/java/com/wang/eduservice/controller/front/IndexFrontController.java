package com.wang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutils.R;
import com.wang.eduservice.pojo.EduCourse;
import com.wang.eduservice.pojo.EduTeacher;
import com.wang.eduservice.service.EduCourseService;
import com.wang.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description="前台显示数据查询")
@RestController
// @CrossOrigin
@RequestMapping("/eduservice/index")
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前8条热门课程
    @Cacheable(value = "course", key = "'selectIndexList'")
    @GetMapping("course")
    public R index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(courseQueryWrapper);

        return R.ok().data("courseList",courseList);
    }

    //查询前4条名师
    @Cacheable(value = "teacher", key = "'selectIndexList'")
    @GetMapping("teacher")
    public R teacher(){
        //查询前4条名师
        QueryWrapper<EduTeacher> teacherQueryWrapper=new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(teacherQueryWrapper);

        return R.ok().data("teacherList",teacherList);
    }
}
