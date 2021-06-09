package com.wang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.R;
import com.wang.eduservice.pojo.EduCourse;
import com.wang.eduservice.pojo.EduTeacher;
import com.wang.eduservice.pojo.vo.CourseInfoFrontVo;
import com.wang.eduservice.pojo.vo.CourseInfoVo;
import com.wang.eduservice.pojo.vo.CoursePublishVo;
import com.wang.eduservice.pojo.vo.CourseQuery;
import com.wang.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-23
 */
@Api(description="课程管理")
@RestController
// @CrossOrigin
@RequestMapping("/eduservice/edu-course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //添加课程基本信息
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String cid = eduCourseService.addCourse(courseInfoVo);
        return R.ok().data("courseId",cid);
    }

    @ApiOperation(value = "根据ID查询课程")
    //根据课程Id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }


    @ApiOperation(value = "修改课程信息")
    //修改课程基本信息
    @PostMapping("updateCourseINfo")
    public R updateCourseINfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //查询将要发布的课程信息
    @ApiOperation(value = "查询将要发布的课程信息")
    @GetMapping("getCoursePublish/{courseId}")
    public R getCoursePublish(@PathVariable String courseId){
        CoursePublishVo coursePublish = eduCourseService.getCoursePublish(courseId);
        return R.ok().data("coursePublish",coursePublish);
    }

    //根据id发布课程
    @ApiOperation(value = "根据id发布课程")
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean b = eduCourseService.updateById(eduCourse);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable Long current, @PathVariable Long limit, @RequestBody CourseQuery courseQuery){
        Page<EduCourse> page = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //构建多条件查询
        String title=courseQuery.getTitle();
        String teacherId=courseQuery.getTeacherId();
        String subjectParentId=courseQuery.getSubjectParentId();
        String subjectId=courseQuery.getSubjectId();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.ge("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.le("subject_id",subjectId);
        }

        eduCourseService.page(page,wrapper);
        long total = page.getTotal();
        List<EduCourse> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //根据id删除课程
    @ApiOperation(value = "根据id删除课程")
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        boolean flag = eduCourseService.deleteCourseById(courseId);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

