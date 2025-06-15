package com.ureca.yoajungadmin.plan.service.response;

import com.ureca.yoajungadmin.user.entity.AgeGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanStatisticResponse {
    private AgeGroup ageGroup;
    private Long value;


    public void addValue(Long value) {
        this.value += value;
    }
}
