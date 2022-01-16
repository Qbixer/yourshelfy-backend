package com.rysiki.yourshelfy.product.api;

import com.rysiki.yourshelfy.product.dto.ProductDTO;
import com.rysiki.yourshelfy.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RequestMapping("/product")
@RestController
public class ProductApi {

    //TODO add CREATE,UPDATE,DELETE for admin (add role admin first)

    @Autowired
    ProductService productService;

    @GetMapping("/all")
    ResponseEntity<Set<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProductsDTO());
    }

}
