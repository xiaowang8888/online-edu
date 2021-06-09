package com.wang.statistics.controller;


import com.wang.commonutils.R;
import com.wang.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author WangMengcheng
 * @since 2021-01-06
 */
@RestController
// @CrossOrigin
@RequestMapping("/statistics/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    //根据日期统计网站数据
    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    //获取数据图表显示
    @GetMapping("showchart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin,@PathVariable String end,@PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }
}

