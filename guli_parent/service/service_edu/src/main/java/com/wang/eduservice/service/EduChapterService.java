package com.wang.eduservice.service;

import com.wang.eduservice.pojo.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.eduservice.pojo.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-23
 */
public interface EduChapterService extends IService<EduChapter> {

    //根据课程ID获取课程章节小节
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节
    boolean deleteChapter(String chapterId);
}
