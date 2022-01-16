package com.rysiki.yourshelfy.shelf.dto;

import com.rysiki.yourshelfy.shelf.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    Integer id;
    String name;
    Set<CategoryProductDTO> products;

    public static CategoryDTO createCategoryDTOFromCategory(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .products(category.getProducts() != null ? category.getProducts().stream().map(CategoryProductDTO::createCategoryProductDTOFromCategoryProduct).collect(Collectors.toSet()) : new HashSet<>())
                .build();
    }

}
