package com.rysiki.yourshelfy.product.dto;

import com.rysiki.yourshelfy.product.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    Integer id;
    String name;

    public static ProductDTO createProductDTOFromProduct(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .build();
    }
}
