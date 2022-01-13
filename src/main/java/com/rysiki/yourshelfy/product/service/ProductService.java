package com.rysiki.yourshelfy.product.service;

import com.rysiki.yourshelfy.product.dto.ProductDTO;
import com.rysiki.yourshelfy.product.entity.Product;
import com.rysiki.yourshelfy.product.entity.ProductCategory;
import com.rysiki.yourshelfy.product.repository.ProductCategoryRepository;
import com.rysiki.yourshelfy.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;


    public Set<ProductDTO> getAllProductsDTO() {
        return productRepository.findAll().stream().map(
                x -> ProductDTO
                        .builder()
                        .id(x.getId())
                        .name(x.getName())
                        .category(x.getCategory() != null ? x.getCategory().getName() : null)
                        .build()
        ).collect(Collectors.toSet());
    }

    public Set<String> getAllProductCategories() {
        return productCategoryRepository.findAll().stream().map(ProductCategory::getName).collect(Collectors.toSet());
    }


}