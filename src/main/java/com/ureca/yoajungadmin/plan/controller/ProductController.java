package com.ureca.yoajungadmin.plan.controller;

import com.ureca.yoajungadmin.common.ApiResponse;
import com.ureca.yoajungadmin.plan.controller.request.CreateProductRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdateProductRequest;
import com.ureca.yoajungadmin.plan.entity.enums.ProductCategory;
import com.ureca.yoajungadmin.plan.entity.enums.ProductType;
import com.ureca.yoajungadmin.s3.service.AwsImgService;
import com.ureca.yoajungadmin.plan.service.ProductService;
import com.ureca.yoajungadmin.plan.service.response.ListProductResponse;
import com.ureca.yoajungadmin.plan.service.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ureca.yoajungadmin.common.BaseCode.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AwsImgService awsImgService;

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
    public ResponseEntity<ApiResponse<ListProductResponse>> getProductList(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return ResponseEntity
                .ok(ApiResponse.of(PRODUCT_LIST_SUCCESS, productService.getProductList(pageNumber - 1, pageSize)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<ListProductResponse>> getAllProductList() {
        return ResponseEntity
                .ok(ApiResponse.of(PRODUCT_LIST_SUCCESS, productService.getAllProductList()));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> editProduct(@PathVariable("productId") Long productId,
                                                      @RequestBody UpdateProductRequest updateProductRequest) {

        productService.updateProduct(productId, updateProductRequest);

        return ResponseEntity
                .ok(ApiResponse.ok(PRODUCT_UPDATE_SUCCESS));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<?>> removeProduct(
            @PathVariable("productId") Long productId, @RequestParam(required = false) String imageUrl) {
        productService.deleteProduct(productId, imageUrl);

        return ResponseEntity
                .ok(ApiResponse.ok(PRODUCT_DELETE_SUCCESS));
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestPart("file") MultipartFile image,
                                         @RequestParam(value = "oldUrl", required = false) String oldUrl) {

        String uploadedUrl = awsImgService.uploadWithOptionalDelete(image, oldUrl);
        return ResponseEntity.status(BENEFIT_CREATE_SUCCESS.getStatus())
                .body(ApiResponse.of(BENEFIT_CREATE_SUCCESS, uploadedUrl));
    }

    @DeleteMapping("/{productId}/image")
    public ResponseEntity<?> deleteImg(@PathVariable("productId") Long productId,
                                       @RequestParam String imageAddr) {
        productService.deleteImageFromProduct(productId, imageAddr);
        return ResponseEntity.status(IMAGE_DELETE_SUCCESS.getStatus())
                .body(ApiResponse.ok(IMAGE_DELETE_SUCCESS));
    }

}
