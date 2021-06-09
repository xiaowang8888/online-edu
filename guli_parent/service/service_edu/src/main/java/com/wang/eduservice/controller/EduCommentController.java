package com.wang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.JwtUtils;
import com.wang.commonutils.Member;
import com.wang.commonutils.R;
import com.wang.eduservice.client.UcenterClient;
import com.wang.eduservice.pojo.EduComment;
import com.wang.eduservice.pojo.EduTeacher;
import com.wang.eduservice.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author WangMengcheng
 * @since 2021-01-04
 */
@Api(description = "课程评论接口")
@RestController
// @CrossOrigin
@RequestMapping("/eduservice/edu-comment")
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;

    //分页查询课程评论
    @ApiOperation(value = "分页查询课程评论")
    @GetMapping("pageComment/{current}/{limit}")
    public R pagelist(@PathVariable Long current, @PathVariable Long limit,String courseId){

        Page<EduComment> pageComment = new Page<>(current, limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");
        eduCommentService.page(pageComment, wrapper);
        List<EduComment> commentList = pageComment.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageComment.getCurrent());
        map.put("pages", pageComment.getPages());
        map.put("size", pageComment.getSize());
        map.put("total", pageComment.getTotal());
        map.put("hasNext", pageComment.hasNext());
        map.put("hasPrevious", pageComment.hasPrevious());
        return R.ok().data(map);
    }

    //添加评论
    @ApiOperation(value = "添加评论")
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(20001).message("请登录");
        }
        eduComment.setMemberId(memberId);
        Member member = ucenterClient.getInfo(memberId);

        eduComment.setNickname(member.getNickname());
        eduComment.setAvatar(member.getAvatar());
        eduCommentService.save(eduComment);
        return R.ok();
    }
}

