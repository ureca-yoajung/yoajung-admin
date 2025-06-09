package com.ureca.yoajungadmin.plan.entity;

import com.ureca.yoajungadmin.common.BaseTimeEntity;
import com.ureca.yoajungadmin.plan.entity.enums.ServiceCategory;
import com.ureca.yoajungadmin.plan.entity.enums.ServiceType;
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
    private ServiceType serviceType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceCategory serviceCategory;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String serviceImage;

    @Builder
    private Product(String name, ServiceType serviceType, ServiceCategory serviceCategory, String description, String serviceImage) {
        this.name = name;
        this.serviceType = serviceType;
        this.serviceCategory = serviceCategory;
        this.description = description;
        this.serviceImage = serviceImage;
    }

    public void update(String name, ServiceType serviceType, ServiceCategory serviceCategory, String description, String serviceImage) {
        this.name = name;
        this.serviceType = serviceType;
        this.serviceCategory = serviceCategory;
        this.description = description;
        this.serviceImage = serviceImage;
    }
}
