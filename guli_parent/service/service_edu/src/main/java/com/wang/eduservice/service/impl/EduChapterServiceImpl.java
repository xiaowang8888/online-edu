package com.wang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.eduservice.pojo.EduChapter;
import com.wang.eduservice.mapper.EduChapterMapper;
import com.wang.eduservice.pojo.EduVideo;
import com.wang.eduservice.pojo.chapter.ChapterVo;
import com.wang.eduservice.pojo.chapter.VideoVo;
import com.wang.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.eduservice.service.EduVideoService;
import com.wang.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    //根据课程ID获取课程章节小节
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据courseId获取所有章节信息
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> chapters = baseMapper.selectList(chapterQueryWrapper);

        //根据courseId获取所有小节信息
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> videos = eduVideoService.list(videoQueryWrapper);

        //创建最终返回的list
        ArrayList<ChapterVo> finalList = new ArrayList<>();
        //将EduChapter中的数据存入ChapterVo中
        for(int i=0;i<chapters.size();i++){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapters.get(i),chapterVo);
            List<VideoVo> videoVos = new ArrayList<>();
            for(int j=0;j<videos.size();j++){
                if(videos.get(j).getChapterId().equals(chapters.get(i).getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(videos.get(j),videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);

            finalList.add(chapterVo);
        }

        return finalList;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节id查询小节表中是否存在数据，如果存在就不能删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        if(count>0){
            throw new GuliException(20001,"删除章节失败，章节中存在数据！");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }
}
