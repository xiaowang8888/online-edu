package com.wang.statistics.service;

import com.wang.statistics.pojo.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author WangMengcheng
 * @since 2021-01-06
 */
public interface DailyService extends IService<Daily> {

    //根据日期统计网站数据
    void createStatisticsByDay(String day);

    //获取数据图表显示
    Map<String, Object> getChartData(String begin, String end, String type);
}
