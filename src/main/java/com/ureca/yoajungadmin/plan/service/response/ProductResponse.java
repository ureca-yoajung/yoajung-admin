package com.ureca.yoajungadmin.plan.service.response;

import com.ureca.yoajungadmin.plan.entity.Product;
import com.ureca.yoajungadmin.plan.entity.enums.ProductCategory;
import com.ureca.yoajungadmin.plan.entity.enums.ProductType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {
    private Long id;
    private String name;
    private ProductType productType;
    private ProductCategory productCategory;
    private String description;
    private String productImage;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .productType(product.getProductType())
                .productCategory(product.getProductCategory())
                .description(product.getDescription())
                .productImage(product.getProductImage())
                .build();
    }
}
