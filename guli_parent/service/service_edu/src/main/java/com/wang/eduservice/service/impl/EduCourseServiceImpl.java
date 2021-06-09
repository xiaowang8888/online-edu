package com.wang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.client.VodClient;
import com.wang.eduservice.pojo.EduChapter;
import com.wang.eduservice.pojo.EduCourse;
import com.wang.eduservice.mapper.EduCourseMapper;
import com.wang.eduservice.pojo.EduCourseDescription;
import com.wang.eduservice.pojo.EduVideo;
import com.wang.eduservice.pojo.vo.CourseInfoFrontVo;
import com.wang.eduservice.pojo.vo.CourseInfoVo;
import com.wang.eduservice.pojo.vo.CoursePublishVo;
import com.wang.eduservice.service.EduChapterService;
import com.wang.eduservice.service.EduCourseDescriptionService;
import com.wang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.eduservice.service.EduVideoService;
import com.wang.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private VodClient vodClient;


    //添加课程基本信息
    @Override
    public String addCourse(CourseInfoVo courseInfoVo) {

        //向课程表中添加数据,先将courseInfoVo中的信息提取到EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);

        //测试接口时使用,数据库中subject_parent_id 设置为非null，所以必须添加数据，不然程序抛出异常
        //解决方法：直接将数据库中字段 subject_parent_id 设置为 允许为null
        // eduCourse.setSubjectParentId("111");

        int insert = baseMapper.insert(eduCourse);

        //如果添加失败，抛出异常
        if(insert<=0){
            throw new GuliException(20001,"课程表中的基本信息添加失败！");
        }

        //如果成功添加至课程表，获取添加记录的id,保持课程描述表记录与课程表一一对应的关系
        String cid=eduCourse.getId();

        //添加课程描述表,此时需要用到EduCourseDescriptionService，直接注入即可
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);

        //如果添加失败，抛出异常
        if(!save){
            throw new GuliException(20001,"课程描述表中的描述信息添加失败！");
        }
        return cid;
    }

    //根据课程Id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //先获取EduCourse
        EduCourse eduCourse = baseMapper.selectById(courseId);

        //在获取EduCourseDescription
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);

        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程基本信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);

        if(update<=0){
            throw new GuliException(20001,"修改课程基本信息失败");
        }

        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        boolean b = eduCourseDescriptionService.updateById(courseDescription);
        if(!b){
            throw new GuliException(20001,"修改课程描述信息失败");
        }
    }

    //查询将要发布的课程信息
    @Override
    public CoursePublishVo getCoursePublish(String id) {
        return baseMapper.selectCoursePublish(id);
    }

    //根据id删除课程
    @Override
    public boolean deleteCourseById(String courseId) {
        //1.删除课时,先删阿里云视频，在删课时
        QueryWrapper<EduVideo> videoQueryWrapper=new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapper);
        List<String> videoIdList=new ArrayList<>();
        for(EduVideo eduVideo:eduVideoList){
            if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                videoIdList.add(eduVideo.getVideoSourceId());
            }
        }
        //调用service-vod中的批量删除接口
        if(videoIdList.size()>0){
            vodClient.deleteVideoBatch(videoIdList);
        }
        boolean remove = eduVideoService.remove(videoQueryWrapper);
        if(!remove){
            throw new GuliException(20001,"删除课时失败！");
        }

        //2.删除章节
        QueryWrapper<EduChapter> chapterQueryWrapper=new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        boolean remove1 = eduChapterService.remove(chapterQueryWrapper);
        if(!remove1){
            throw new GuliException(20001,"删除章节失败！");
        }
        //3.删除描述
        boolean b = eduCourseDescriptionService.removeById(courseId);
        if(!b){
            throw new GuliException(20001,"删除课程描述失败！");
        }
        //4.删除课程信息
        int i = baseMapper.deleteById(courseId);
        return i>0;
    }

    //根据课程id查询，编写sql语句查询课程信息(前台详情页)
    @Override
    public CourseInfoFrontVo getFrontCourseInfo(String id) {
        return baseMapper.getFrontCourseInfo(id);
    }


}
