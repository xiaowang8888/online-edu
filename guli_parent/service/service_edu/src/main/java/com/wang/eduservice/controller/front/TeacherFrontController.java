package com.wang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.R;
import com.wang.eduservice.pojo.EduCourse;
import com.wang.eduservice.pojo.EduTeacher;
import com.wang.eduservice.service.EduCourseService;
import com.wang.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
// @CrossOrigin
@Api(description="前台讲师页面方法")
@RequestMapping("/eduservice/teacherFront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    //分页查询讲师
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String,Object> map = eduTeacherService.getTeacherFrontList(teacherPage);
        return R.ok().data(map);
    }

    //讲师详情页，根据讲师id查询所授课程
    @GetMapping("getCourse/{id}")
    public R getByTeacherId(@PathVariable String id){
        //根据id获取讲师信息
        EduTeacher teacher = eduTeacherService.getById(id);

        //根据讲师id获取课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        //按照最后更新时间倒序排列
        wrapper.orderByDesc("gmt_modified");
        List<EduCourse> courses = eduCourseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courses);
    }

}
