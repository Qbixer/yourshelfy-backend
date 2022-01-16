package com.rysiki.yourshelfy.shelf.dto;

import com.rysiki.yourshelfy.product.dto.ProductDTO;
import com.rysiki.yourshelfy.shelf.entity.CategoryProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryProductDTO {

    ProductDTO product;
    Integer amount;

    public static CategoryProductDTO createCategoryProductDTOFromCategoryProduct(CategoryProduct categoryProduct) {
        return CategoryProductDTO.builder()
                .product(ProductDTO.createProductDTOFromProduct(categoryProduct.getProduct()))
                .amount(categoryProduct.getAmount())
                .build();
    }
}
