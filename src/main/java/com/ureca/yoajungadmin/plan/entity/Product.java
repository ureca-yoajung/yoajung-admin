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
@Table(name="product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name="productType", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name="productCategory", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name="productImage", nullable = false)
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

    public void removeImage() {
        this.productImage = "";
    }
}
