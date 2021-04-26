package com.java.assessment.product.service.impl;

import com.java.assessment.product.dto.ProductDto;
import com.java.assessment.product.model.Product;
import com.java.assessment.product.repository.ProductRepository;
import com.java.assessment.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductDto> findAllProducts() {

        List<Product> products = productRepository.findAll();
        return mapProductListToProductDtoList(products);
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if( product.isPresent()) {
            return mapProductToProductDto(product.get());
        }
        return null;
    }

    @Override
    public Product save( Product product ){
        return productRepository.save(product);
    }

    @Override
    public ProductDto findByProductCode(String productCode) {
        Optional<Product> product = productRepository.findByCode(productCode);
        if( product.isPresent()) {
            return mapProductToProductDto(product.get());
        }
        return null;
    }

    private ProductDto mapProductToProductDto( Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setName(product.getName());
        productDto.setCode(product.getCode());
        return productDto;
    }
    private List<ProductDto> mapProductListToProductDtoList( List<Product> productList) {
        return productList.stream()
                .map(product -> mapProductToProductDto( product)).collect(Collectors.toList());
    }

}
