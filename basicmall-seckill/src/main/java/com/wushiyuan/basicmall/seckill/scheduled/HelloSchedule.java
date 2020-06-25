package com.wushiyuan.basicmall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@Slf4j
@Component
@EnableScheduling
public class HelloSchedule {

    /**
     * 1、Spring 中 6 位组成，不允许第 7 位的年
     * 2、在周几的位置 1-7 分别代表周一到周日
     * 3、定时任务不应该阻塞。默认是阻塞的
     *      如何不阻塞
     *      1）、可以让业务以异步的方式，自己提交到线程池
     *      2）、支持定时任务线程池
     *      3）、异步任务
     */
//    @Scheduled(cron = "* * * * * 2")
//    @Async
    public void hello() throws InterruptedException {
        log.info("hello...");
        Thread.sleep(3000);
    }
}
