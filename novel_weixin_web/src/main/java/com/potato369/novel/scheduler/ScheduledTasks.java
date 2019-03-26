package com.potato369.novel.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import com.potato369.novel.task.AddSubscribeUserTask;
import com.potato369.novel.task.CancelUnpaidTimeoutOrderTask;

/**
 * <pre>
 * @PackageName com.potato369.novel.task
 * @ClassName AddSubscribeUserScheduledTasks
 * @Desc AddSubscribeUserScheduledTasks
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/3/22 14:19
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Component
@Slf4j
public class ScheduledTasks {

    @Autowired
    private AddSubscribeUserTask task1;

    @Autowired
    private CancelUnpaidTimeoutOrderTask task2;

    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("scheduled-thread-");
        return scheduler;
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
//    @Scheduled(fixedDelay = 5000)
    public void addSubscribeUserScheduled() {
        task1.addSubscribeUserTask();
    }

    @Scheduled(cron = "0 0/30 * * * ?")
//    @Scheduled(fixedDelay = 5000)
    public void cancelUnpaidTimeoutOrder() {
        task2.cancelsUnpaidTimeoutOrderTask();
    }
}
