package com.ureca.yoajungadmin.plan.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.plan.controller.request.CreateProductRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdateProductRequest;
import com.ureca.yoajungadmin.plan.entity.enums.ProductCategory;
import com.ureca.yoajungadmin.plan.entity.enums.ProductType;
import com.ureca.yoajungadmin.plan.service.ProductService;
import com.ureca.yoajungadmin.plan.service.response.ListProductResponse;
import com.ureca.yoajungadmin.plan.service.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ureca.yoajungadmin.common.BaseCode.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/enums/service-types")
    public ResponseEntity<ApiResponse<ProductType[]>> getProductTypeList() {
        return ResponseEntity
                .ok(ApiResponse.of(SERVICE_TYPE_LIST_SUCCESS, ProductType.values()));
    }

    @GetMapping("/enums/service-categories")
    public ResponseEntity<ApiResponse<ProductCategory[]>> getProductCategoryList() {
        return ResponseEntity
                .ok(ApiResponse.of(SERVICE_CATEGORY_LIST_SUCCESS, ProductCategory.values()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        productService.createProduct(createProductRequest);

        return ResponseEntity
                .status(PRODUCT_CREATE_SUCCESS.getStatus())
                .body(ApiResponse.ok(PRODUCT_CREATE_SUCCESS));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long productId) {
        return ResponseEntity
                .ok(ApiResponse.of(PRODUCT_DETAIL_SUCCESS, productService.getProduct(productId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ListProductResponse>> getProductList() {
        return ResponseEntity
                .ok(ApiResponse.of(PRODUCT_LIST_SUCCESS, productService.getProductList()));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> editProduct(@PathVariable("productId") Long productId,
                                                      @RequestBody UpdateProductRequest updateProductRequest) {

        productService.updateProduct(productId, updateProductRequest);

        return ResponseEntity
                .ok(ApiResponse.ok(PRODUCT_UPDATE_SUCCESS));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> removeProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity
                .ok(ApiResponse.ok(PRODUCT_DELETE_SUCCESS));
    }
}
