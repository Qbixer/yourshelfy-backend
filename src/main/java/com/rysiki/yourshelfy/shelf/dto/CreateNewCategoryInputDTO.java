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
public class CreateNewCategoryInputDTO {

    Integer shelfId;
    String name;


}
