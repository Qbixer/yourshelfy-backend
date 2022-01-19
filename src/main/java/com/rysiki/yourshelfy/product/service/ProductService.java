package com.rysiki.yourshelfy.product.service;

import com.rysiki.yourshelfy.product.dto.ProductDTO;
import com.rysiki.yourshelfy.product.entity.Product;
import com.rysiki.yourshelfy.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public static class BlankProductNameException extends RuntimeException {
        public BlankProductNameException(String message) {
            super(message);
        }
    }

    public static class ProductNotExists extends RuntimeException {
        public ProductNotExists(String message) {
            super(message);
        }
    }

    @Autowired
    ProductRepository productRepository;


    public Set<ProductDTO> getAllProductsDTO() {
        return productRepository.findAll().stream().map(
                x -> ProductDTO
                        .builder()
                        .id(x.getId())
                        .name(x.getName())
                        .build()
        ).collect(Collectors.toSet());
    }

    public Product getOrCreateProduct(String name) {
        if(name == null || name.isBlank()) {
            throw new BlankProductNameException("Blank product name");
        }
        name = name.substring(0,1).toUpperCase().concat(name.substring(1).toLowerCase());
        Optional<Product> optionalProduct = productRepository.findByName(name);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            Product product = new Product();
            product.setName(name);
            return productRepository.save(product);
        }
    }
}