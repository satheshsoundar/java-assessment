package com.java.assessment.product.service;

import com.java.assessment.product.dto.ProductDto;
import com.java.assessment.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductDto findById(Long id);

    List<ProductDto> findAllProducts();

    Product save(Product product);

    ProductDto findByProductCode(String productCode);
}
