package yll.common.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务
 * @author cc
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    // ==============================Fields===========================================
    @Autowired
    private MyScheduleMethods myScheduleMethods;

    // ==============================ScheduleMethods==========================================
    //表达式指定，例如：5秒
    //@Scheduled(cron = "5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    //每5分钟 触发一次
    //@Scheduled(cron = "5 * * * * ?")
    //每天0点 的10秒 触发一次
    //@Scheduled(cron = "10 0 0 * * ?")


    //招聘信息定时任务 - 每天0点 的10秒 触发一次
    @Scheduled(cron = "10 0 0 * * ?")
    private void recruitTasks() {
        System.err.println("执行招聘下架静态定时任务时间: " + LocalDateTime.now());
        myScheduleMethods.recruitEnterpriseDetailsStateExecute();
    }


    //=================定时任务-业务方法===================


}
