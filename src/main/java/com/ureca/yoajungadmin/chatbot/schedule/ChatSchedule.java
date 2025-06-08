package com.ureca.yoajungadmin.chatbot.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class ChatSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void runDailyMessage() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("dailyMessageJob"), jobParameters);
    }

//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void runDailyMessageUser() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("dailyMessageUserJob"), jobParameters);
    }

//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void runWeeklyMessage() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("weeklyMessageJob"), jobParameters);
    }

//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void runWeeklyMessageUser() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("weeklyMessageUserJob"), jobParameters);
    }

        @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void runMonthlyMessage() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("monthlyMessageJob"), jobParameters);
    }

        @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void runMonthlyMessageUser() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("monthlyMessageUserJob"), jobParameters);
    }
}
