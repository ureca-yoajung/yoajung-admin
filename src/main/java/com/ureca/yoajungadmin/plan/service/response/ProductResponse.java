package com.ureca.yoajungadmin.plan.service.response;

import com.ureca.yoajungadmin.plan.entity.Product;
import com.ureca.yoajungadmin.plan.entity.enums.ServiceCategory;
import com.ureca.yoajungadmin.plan.entity.enums.ServiceType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {
    private Long id;
    private String name;
    private ServiceType serviceType;
    private ServiceCategory serviceCategory;
    private String description;
    private String serviceImage;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .serviceType(product.getServiceType())
                .serviceCategory(product.getServiceCategory())
                .description(product.getDescription())
                .serviceImage(product.getServiceImage())
                .build();
    }
}
