package com.ureca.yoajungadmin.plan.service.impl;

import com.ureca.yoajungadmin.plan.controller.request.CreateProductRequest;
import com.ureca.yoajungadmin.plan.controller.request.UpdateProductRequest;
import com.ureca.yoajungadmin.plan.entity.Product;
import com.ureca.yoajungadmin.plan.exception.ProductNotFoundException;
import com.ureca.yoajungadmin.plan.repository.ProductRepository;
import com.ureca.yoajungadmin.plan.service.ProductService;
import com.ureca.yoajungadmin.plan.service.response.ListProductResponse;
import com.ureca.yoajungadmin.plan.service.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ureca.yoajungadmin.common.BaseCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Long createProduct(CreateProductRequest createProductRequest) {
        Product product = Product.builder()
                .name(createProductRequest.getName())
                .productType(createProductRequest.getProductType())
                .productCategory(createProductRequest.getProductCategory())
                .description(createProductRequest.getDescription())
                .productImage(createProductRequest.getProductImage())
                .build();

        productRepository.save(product);

        return product.getId();
    }

    @Override
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        return ProductResponse.from(product);
    }

    @Override
    public ListProductResponse getProductList(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());

        Page<Product> productPage = productRepository.findAll(pageRequest);

        List<ProductResponse> productList = productPage.getContent()
                .stream()
                .map(ProductResponse::from)
                .toList();

        return new ListProductResponse(productList, productPage.getNumber() + 1, productPage.getSize(), productPage.getTotalElements());
    }

    @Override
    public void updateProduct(Long productId, UpdateProductRequest updateProductRequest) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        product.update(
                updateProductRequest.getName(),
                updateProductRequest.getProductType(),
                updateProductRequest.getProductCategory(),
                updateProductRequest.getDescription(),
                updateProductRequest.getProductImage()
        );
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
