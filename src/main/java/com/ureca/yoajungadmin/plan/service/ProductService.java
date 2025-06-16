package com.ureca.yoajungadmin.plan.service;

import com.ureca.yoajungadmin.plan.controller.request.CreateProductRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdateProductRequest;
import com.ureca.yoajungadmin.plan.service.response.ListProductResponse;
import com.ureca.yoajungadmin.plan.service.response.ProductResponse;

public interface ProductService {

    Long createProduct(CreateProductRequest createProductRequest);
    ProductResponse getProduct(Long productId);
    ListProductResponse getProductList(Integer pageNumber, Integer pageSize);
    void updateProduct(Long productId, UpdateProductRequest updateProductRequest);
    void deleteProduct(Long productId, String imageUrl);
    void deleteImageFromProduct(Long productId, String imageAddr);
}
