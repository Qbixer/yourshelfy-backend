package com.rysiki.yourshelfy.shelf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductChangeNameInputDTO {

    Integer productId;
    String newName;


}
