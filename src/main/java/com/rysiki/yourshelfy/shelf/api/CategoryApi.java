package com.rysiki.yourshelfy.shelf.api;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import com.rysiki.yourshelfy.shelf.dto.CategoryDTO;
import com.rysiki.yourshelfy.shelf.dto.CreateNewCategoryInputDTO;
import com.rysiki.yourshelfy.shelf.dto.ShelfDTO;
import com.rysiki.yourshelfy.shelf.entity.Category;
import com.rysiki.yourshelfy.shelf.entity.Shelf;
import com.rysiki.yourshelfy.shelf.service.CategoryService;
import com.rysiki.yourshelfy.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/category")
@RestController
public class CategoryApi {

    @Autowired
    MyUserService myUserService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("create")
    CategoryDTO createNewShelf(@RequestBody CreateNewCategoryInputDTO createNewCategoryInputDTO) {
        MyUser currentUser = myUserService.getCurrentUser();
        Category newCategory = categoryService.createNewCategory(currentUser, createNewCategoryInputDTO.getShelfId(), createNewCategoryInputDTO.getName());
        return CategoryDTO.createCategoryDTOFromCategory(newCategory);
    }

    @PutMapping("rename/{id}")
    CategoryDTO renameCategory(@PathVariable Integer id, @RequestBody String name) {
        MyUser currentUser = myUserService.getCurrentUser();
        Category category = categoryService.renameCategory(currentUser, id, name);
        return CategoryDTO.createCategoryDTOFromCategory(category);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity deleteCategory(@PathVariable Integer id) {
        MyUser currentUser = myUserService.getCurrentUser();
        categoryService.deleteCategory(currentUser, id);
        return ResponseEntity.ok().build();
    }
}
