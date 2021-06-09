package com.wang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.JwtUtils;
import com.wang.commonutils.R;
import com.wang.eduservice.client.OrderClient;
import com.wang.eduservice.pojo.EduCourse;
import com.wang.eduservice.pojo.chapter.ChapterVo;
import com.wang.eduservice.pojo.vo.CouresQueryFront;
import com.wang.eduservice.pojo.vo.CourseInfoFrontVo;
import com.wang.eduservice.service.EduChapterService;
import com.wang.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// @CrossOrigin
@Api(description="前台课程页面方法")
@RequestMapping("/eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    //条件查询带分页
    @PostMapping("pageCourseCondition/{page}/{limit}")
    public R pageCourseCondition(@PathVariable long page, @PathVariable long limit, @RequestBody CouresQueryFront couresQueryFront){
        Page<EduCourse> coursePage = new Page<>(page, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //构建多条件查询
        String subjectParentId=couresQueryFront.getSubjectParentId();
        String subjectId=couresQueryFront.getSubjectId();
        String buyCountSort=couresQueryFront.getBuyCountSort();
        String gmtCreateSort=couresQueryFront.getGmtCreateSort();
        String priceSort=couresQueryFront.getPriceSort();
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(buyCountSort)){
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(gmtCreateSort)){
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(priceSort)){
            wrapper.orderByAsc("price");
        }
        eduCourseService.page(coursePage,wrapper);

        //将分页数据封装到一个map
        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return R.ok().data(map);
    }

    //根据课程id查询课程基本信息
    @GetMapping("geFrontCourseInfo/{id}")
    public R getFrontCourseInfo(@PathVariable String id, HttpServletRequest request){
        //根据课程id查询，编写sql语句查询课程信息
        CourseInfoFrontVo courseInfoFrontVo = eduCourseService.getFrontCourseInfo(id);

        //根据课程id查询章节小节
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(id);

        //远程调用，判断课程是否被购买
        boolean isbuyCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), id);
        return R.ok().data("courseInfoFrontVo",courseInfoFrontVo).data("list",list).data("isbuyCourse",isbuyCourse);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getDto/{id}")
    public CourseInfoFrontVo getDto(@PathVariable String id){
        //根据课程id查询，编写sql语句查询课程信息
        CourseInfoFrontVo courseInfoFrontVo = eduCourseService.getFrontCourseInfo(id);
        return courseInfoFrontVo;
    }
}
