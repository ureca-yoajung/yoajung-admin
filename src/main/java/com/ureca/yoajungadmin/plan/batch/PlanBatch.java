package com.ureca.yoajungadmin.plan.batch;

import com.ureca.yoajungadmin.plan.entity.PlanStatistic;
import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import com.ureca.yoajungadmin.plan.repository.PlanRepository;
import com.ureca.yoajungadmin.plan.repository.PlanStatisticRepository;
import com.ureca.yoajungadmin.plan.repository.custom.PopularPlanDto;
import com.ureca.yoajungadmin.user.entity.AgeGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class PlanBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final PlanRepository planRepository;
    private final PlanStatisticRepository planStatisticRepository;

    @Bean
    public Job planJob() {
        return new JobBuilder("planJob", jobRepository)
                .start(planStep())
                .build();

    }

    @Bean
    public Step planStep() {
        return new StepBuilder("planStep", jobRepository)
                .<PopularPlanDto, PlanStatistic> chunk(100, platformTransactionManager)
                .reader(popularPlanDtoRepositoryItemReader())
                .processor(popularPlanDtoPlanStatisticItemProcessor())
                .writer(planStatisticRepositoryItemWriter())
                .faultTolerant()
                .retry(Exception.class)
                .retryLimit(3)
                .build();
    }

    @Bean
    @StepScope
    public ListItemReader<PopularPlanDto> popularPlanDtoRepositoryItemReader() {
        List<PopularPlanDto> buffer = new ArrayList<>();

        for (AgeGroup ag : AgeGroup.values()) {
            for (PlanCategory pc : PlanCategory.values()) {
                buffer.addAll(planRepository.findOverallPopularPlans(ag, pc));
            }
        }

        return new ListItemReader<>(buffer);
    }

    @Bean
    public ItemProcessor<PopularPlanDto, PlanStatistic> popularPlanDtoPlanStatisticItemProcessor() {
        return item -> PlanStatistic.builder()
                .planId(item.getPlanId())
                .ageGroup(item.getAgeGroup())
                .planCategory(item.getPlanCategory())
                .userCount(item.getUserCount())
                .build();
    }

    @Bean
    public RepositoryItemWriter<PlanStatistic> planStatisticRepositoryItemWriter() {
        return new RepositoryItemWriterBuilder<PlanStatistic>()
                .repository(planStatisticRepository)
                .methodName("save")
                .build();
    }
}
