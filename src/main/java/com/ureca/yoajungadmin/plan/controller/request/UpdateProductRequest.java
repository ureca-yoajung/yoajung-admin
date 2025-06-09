package com.ureca.yoajungadmin.plan.controller.request;

import com.ureca.yoajungadmin.plan.entity.enums.ServiceCategory;
import com.ureca.yoajungadmin.plan.entity.enums.ServiceType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateProductRequest {
    private String name;
    private ServiceType serviceType;
    private ServiceCategory serviceCategory;
    private String description;
    private String serviceImage;
}
