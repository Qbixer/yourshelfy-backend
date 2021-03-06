package com.rysiki.yourshelfy.shelf.api;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import com.rysiki.yourshelfy.shelf.dto.*;
import com.rysiki.yourshelfy.shelf.entity.Category;
import com.rysiki.yourshelfy.shelf.entity.CategoryProduct;
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

    @PostMapping("{categoryId}/addProduct")
    CategoryProductDTO addNewProductToShelf(@PathVariable Integer categoryId, @RequestBody String productName) {
        MyUser currentUser = myUserService.getCurrentUser();
        CategoryProduct categoryProduct = categoryService.addNewProductToCategory(currentUser, categoryId, productName, 0);
        return CategoryProductDTO.createCategoryProductDTOFromCategoryProduct(categoryProduct);
    }

    @DeleteMapping("{categoryId}/deleteProduct/{productId}")
    ResponseEntity addNewProductToShelf(@PathVariable Integer categoryId, @PathVariable Integer productId) {
        MyUser currentUser = myUserService.getCurrentUser();
        categoryService.deleteCategoryProduct(currentUser, categoryId, productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{categoryId}/changeProductAmount")
    CategoryProductDTO changeAmountOfCategoryProduct(@PathVariable Integer categoryId, @RequestBody ProductDeltaInputDTO productDeltaInputDTO) {
        MyUser currentUser = myUserService.getCurrentUser();
        CategoryProduct categoryProduct = categoryService.changeAmountOfCategoryProduct(currentUser, categoryId, productDeltaInputDTO.getProductId(), productDeltaInputDTO.getDelta());
        return CategoryProductDTO.createCategoryProductDTOFromCategoryProduct(categoryProduct);
    }

    @PutMapping("{categoryId}/changeProductName")
    CategoryProductDTO changeNameOfCategoryProduct(@PathVariable Integer categoryId, @RequestBody ProductChangeNameInputDTO productChangeNameInputDTO) {
        MyUser currentUser = myUserService.getCurrentUser();
        CategoryProduct categoryProduct = categoryService.changeNameOfCategoryProduct(currentUser, categoryId, productChangeNameInputDTO.getProductId(), productChangeNameInputDTO.getNewName());
        return CategoryProductDTO.createCategoryProductDTOFromCategoryProduct(categoryProduct);
    }

    @PostMapping("addProductToShoppingList")
    CategoryDTO addProductToShoppingList(@RequestBody AddProductToShoppingListInputDTO addProductToShoppingListInputDTO) {
        MyUser currentUser = myUserService.getCurrentUser();
        Category category = categoryService.addNewProductToShoppingList(currentUser, addProductToShoppingListInputDTO.getCategoryName(), addProductToShoppingListInputDTO.getProductId(), addProductToShoppingListInputDTO.getAmount());
        return CategoryDTO.createCategoryDTOFromCategory(category);
    }

}
