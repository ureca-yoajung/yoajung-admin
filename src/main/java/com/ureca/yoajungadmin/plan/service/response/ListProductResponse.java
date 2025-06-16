package com.ureca.yoajungadmin.plan.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListProductResponse {

    private List<ProductResponse> productResponseList;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
}
