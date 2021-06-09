package com.wang.eduservice.controller;


import com.wang.commonutils.R;
import com.wang.eduservice.pojo.subject.OneSubject;
import com.wang.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-22
 */
@Api(description="课程分类管理")
@RestController
@RequestMapping("/eduservice/edu-subject")
// @CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //通过excel添加课程分类
    //获取上传的文件
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //获取课程分类列表（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

