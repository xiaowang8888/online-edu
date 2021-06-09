package com.wang.statistics.schedule;

import com.wang.statistics.service.DailyService;
import com.wang.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private DailyService dailyService;

    /**
     * 测试
     * 每天七点到二十三点每五秒执行一次
     * cron表达式，SpringBoot默认指定为当前年份故只有六位
     */
    // @Scheduled(cron = "0/5 * * * * ?")
    // public void task1() {
    //     System.out.println("*********++++++++++++*****执行了");
    // }

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.createStatisticsByDay(day);
    }
}
