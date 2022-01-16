package com.rysiki.yourshelfy.shelf.dto;

import com.rysiki.yourshelfy.auth.dto.UserProfileDTO;
import com.rysiki.yourshelfy.shelf.entity.Shelf;
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
public class ShelfDTO {

    Integer id;
    String name;
    UserProfileDTO owner;
    Boolean isShoppingList;
    Boolean isNullShelf;

    Set<CategoryDTO> categories;

    public static ShelfDTO createShelfDTOFromShelf(Shelf shelf) {
        return ShelfDTO.builder()
                .id(shelf.getId())
                .name(shelf.getName())
                .owner(UserProfileDTO.createUserProfileDTOFromMyUser(shelf.getOwner()))
                .isShoppingList(shelf.getIsShoppingList())
                .isNullShelf(shelf.getIsNullShelf())
                .categories(shelf.getCategories() != null ? shelf.getCategories().stream().map(CategoryDTO::createCategoryDTOFromCategory).collect(Collectors.toSet()) : new HashSet<>())
                .build();
    }
}
