package com.wang.eduservice.service;

import com.wang.eduservice.pojo.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.pojo.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-22
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    //获取所有课程分类
    List<OneSubject> getAllOneTwoSubject();
}
