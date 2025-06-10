package com.ureca.yoajungadmin.plan.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ureca.yoajungadmin.plan.entity.enums.PlanCategory;
import com.ureca.yoajungadmin.user.entity.AgeGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ureca.yoajungadmin.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class PlanRepositoryImpl implements PlanRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PopularPlanDto> findOverallPopularPlans(AgeGroup ageGroup, PlanCategory category) {
        BooleanBuilder builder = new BooleanBuilder();

        if (ageGroup != AgeGroup.ALL) {
            builder.and(user.ageGroup.eq(ageGroup));
        }

        if (category != PlanCategory.ALL) {
            builder.and(user.plan.planCategory.eq(category));
        }

        return queryFactory
                .select(Projections.constructor(PopularPlanDto.class,
                        user.plan.id,
                        user.count(),
                        Expressions.constant(ageGroup),
                        Expressions.constant(category)
                ))
                .from(user)
                .join(user.plan)
                .where(builder)
                .groupBy(user.plan.id)
                .fetch();


    }
}
