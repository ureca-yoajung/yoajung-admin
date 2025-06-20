package com.ureca.yoajungadmin.chatbot.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.common.BaseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatBotBatchController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;


    @GetMapping("/batch/chat-bot/run")
    public ResponseEntity<ApiResponse<?>> executeForcedChatBotBatch() throws NoSuchJobException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        LocalDate startBatchDay = LocalDate.now();
        // 일
        for (int i = 6; i >= 0; i--) {
            LocalDate localDate = startBatchDay.minusDays(i);
            String date = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("date", date)
                    .toJobParameters();
            try {
                jobLauncher.run(jobRegistry.getJob("dailyMessageJob"), jobParameters);
                log.info("dailyMessageJob OK for {}", date);
            } catch (Exception e) {
                log.error("dailyMessageJob failed for {}", date, e);
            }
            try {
                jobLauncher.run(jobRegistry.getJob("dailyMessageUserJob"), jobParameters);
                log.info("dailyMessageUserJob OK for {}", date);
            } catch (Exception e) {
                log.error("dailyMessageUserJob failed for {}", date, e);
            }
        }

        // 주
        LocalDate sunday = startBatchDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        for (int i = 6; i >= 0; i--) {
            LocalDate statSunday = sunday.minusWeeks(i);
            String date = statSunday.format(DateTimeFormatter.ISO_LOCAL_DATE);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("date", date)
                    .toJobParameters();

            try {
                jobLauncher.run(jobRegistry.getJob("weeklyMessageJob"), jobParameters);
                log.info("weeklyMessageJob OK for {}", date);
            } catch (Exception e) {
                log.error("weeklyMessageJob failed for {}", date, e);
            }

            try {
                jobLauncher.run(jobRegistry.getJob("weeklyMessageUserJob"), jobParameters);
                log.info("weeklyMessageUserJob OK for {}", date);
            } catch (Exception e) {
                log.error("weeklyMessageUserJob failed for {}", date, e);
            }
        }

        // 월
        LocalDate firstOfMonth = startBatchDay.withDayOfMonth(1);

        for (int i = 6; i >= 0; i--) {
            LocalDate statFirst = firstOfMonth.minusMonths(i);
            String date = statFirst.format(DateTimeFormatter.ISO_LOCAL_DATE);

            JobParameters params = new JobParametersBuilder()
                    .addString("date", date)
                    .toJobParameters();

            try {
                jobLauncher.run(jobRegistry.getJob("monthlyMessageJob"), params);
                log.info("monthlyMessageJob OK for {}", date);
            } catch (Exception e) {
                log.error("monthlyMessageJob failed for {}", date, e);
            }
            try {
                jobLauncher.run(jobRegistry.getJob("monthlyMessageUserJob"), params);
                log.info("monthlyMessageUserJob OK for {}", date);
            } catch (Exception e) {
                log.error("monthlyMessageUserJob failed for {}", date, e);
            }
        }

        return ResponseEntity.ok(ApiResponse.ok(BaseCode.STATUS_OK));
    }
}
