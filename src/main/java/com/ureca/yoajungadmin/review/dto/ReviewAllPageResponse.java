package com.ureca.yoajungadmin.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewAllPageResponse {
    private List<ReviewListResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public ReviewAllPageResponse(Page<ReviewListResponse> pageData) {
        this.content = pageData.getContent();
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalElements = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
    }
}
