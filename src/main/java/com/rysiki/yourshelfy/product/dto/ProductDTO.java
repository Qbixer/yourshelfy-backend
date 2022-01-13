package com.rysiki.yourshelfy.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    Integer id;
    String name;
    String category;

    // Base64 representation of icon OR or just "url" (id) to this icon
    String icon;
}
