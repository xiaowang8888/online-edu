package com.wang.eduservice.controller;


import com.wang.commonutils.R;
import com.wang.eduservice.pojo.EduChapter;
import com.wang.eduservice.pojo.chapter.ChapterVo;
import com.wang.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
// @CrossOrigin
@Api(description="课程章节管理")
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //根据课程ID获取课程章节小节
    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("list",list);
    }
    //添加章节
    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //根据章节id查询
    @ApiOperation(value = "根据章节id查询章节")
    @GetMapping("getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateById(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节
    @ApiOperation(value = "删除章节")
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteById(@PathVariable String chapterId){
        boolean flag=eduChapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

