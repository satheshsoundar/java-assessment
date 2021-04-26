package com.java.assessment.product.controller;

import com.java.assessment.product.dto.ProductDto;
import com.java.assessment.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductDto> shoppingCart() {
        return productService.findAllProducts();
    }

    @GetMapping("/product/{productId}")
    public ProductDto getProduct(@PathVariable("productId") Long productId) {
        return productService.findById(productId);
    }
    @GetMapping("/product/code/{productCode}")
    public ProductDto getProductByCode(@PathVariable("productCode") String productCode) {
        return productService.findByProductCode(productCode);
    }
}
