package com.wang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.R;
import com.wang.eduservice.pojo.EduTeacher;
import com.wang.eduservice.pojo.vo.TeacherQuery;
import com.wang.eduservice.service.EduTeacherService;
import com.wang.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-18
 */
@Api(description = "讲师管理")
@RestController
// @CrossOrigin
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 1.查询edu_teacher中所有数据
     * 2.采用rest风格
     * 3.访问地址：http://localhost:8001/eduservice/edu-teacher/findAll
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R fingAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("teachers",list);
    }

    /**
     * 1.逻辑删除教师数据
     * 2.采用rest风格
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R deleteTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }
    /**
     * 1.分页查询讲师功能
     */
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pagelist(
            @ApiParam(name="current",value = "当前页码",required = true)
            @PathVariable Long current,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit
    ){

        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        eduTeacherService.page(pageTeacher, null);
        List<EduTeacher> records = pageTeacher.getRecords();
        //总记录数
        long total = pageTeacher.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    /**
     * 1.条件查询加分页
     */
    @ApiOperation(value = "多条件查询加分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @ApiParam(name="current",value = "当前页码",required = true)
            @PathVariable Long current,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @RequestBody(required = false) TeacherQuery teacherQuery
    ){
        Page<EduTeacher> page = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //构建多条件查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        //按照加入时间排序
        wrapper.orderByDesc("gmt_create");

        eduTeacherService.page(page,wrapper);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 1.添加讲师
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 1.根据讲师ID进行查询
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    /**
     * 1.修改讲师
     */
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        //自定义异常需要自己抛出
        // try {
        //     int i=10/0;
        // }catch (Exception e){
        //     throw new GuliException(20001,"执行了自定义异常处理！");
        // }
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

