package com.ureca.yoajungadmin.plan.entity;

import com.ureca.yoajungadmin.common.BaseTimeEntity;
import com.ureca.yoajungadmin.plan.entity.enums.ProductCategory;
import com.ureca.yoajungadmin.plan.entity.enums.ProductType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String productImage;

    @Builder
    private Product(String name, ProductType productType, ProductCategory productCategory, String description, String productImage) {
        this.name = name;
        this.productType = productType;
        this.productCategory = productCategory;
        this.description = description;
        this.productImage = productImage;
    }

    public void update(String name, ProductType productType, ProductCategory productCategory, String description, String productImage) {
        this.name = name;
        this.productType = productType;
        this.productCategory = productCategory;
        this.description = description;
        this.productImage = productImage;
    }
}
