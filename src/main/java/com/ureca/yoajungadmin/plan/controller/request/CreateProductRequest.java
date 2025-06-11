package com.ureca.yoajungadmin.plan.controller.request;

import com.ureca.yoajungadmin.plan.entity.enums.ProductCategory;
import com.ureca.yoajungadmin.plan.entity.enums.ProductType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateProductRequest {
    private String name;
    private ProductType productType;
    private ProductCategory productCategory;
    private String description;
    private String productImage;
}
