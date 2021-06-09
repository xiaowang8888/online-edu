package com.wang.eduservice.controller;


import com.wang.commonutils.R;
import com.wang.eduservice.client.VodClient;
import com.wang.eduservice.pojo.EduVideo;
import com.wang.eduservice.pojo.vo.CourseInfoVo;
import com.wang.eduservice.service.EduVideoService;
import com.wang.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author WangMengcheng
 * @since 2020-12-23
 */
@Api(description = "视频小节管理")
@RestController
// @CrossOrigin
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;
    //注入服务调用接口
    @Autowired
    private VodClient vodClient;

    //添加视频小节
    @ApiOperation(value = "添加视频小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //根据Id查询视频小节
    @ApiOperation(value = "根据ID查询视频小节")
    @GetMapping("getVideo/{id}")
    public R getVideoInfo(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改视频小节
    @ApiOperation(value = "修改视频小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    //删除视频小节,同时删除视频数据
    @ApiOperation(value = "删除视频小节")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        //先根据小节id得到视频id
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            R result = vodClient.deleteVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败,熔断器...");
            }
        }
        boolean flag = eduVideoService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

