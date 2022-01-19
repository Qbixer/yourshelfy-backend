package com.rysiki.yourshelfy.shelf.service;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import com.rysiki.yourshelfy.product.entity.Product;
import com.rysiki.yourshelfy.product.repository.ProductRepository;
import com.rysiki.yourshelfy.product.service.ProductService;
import com.rysiki.yourshelfy.shelf.dto.CategoryDTO;
import com.rysiki.yourshelfy.shelf.entity.Category;
import com.rysiki.yourshelfy.shelf.entity.CategoryProduct;
import com.rysiki.yourshelfy.shelf.entity.Shelf;
import com.rysiki.yourshelfy.shelf.repository.CategoryRepository;
import com.rysiki.yourshelfy.shelf.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    public static class BlankCategoryNameException extends RuntimeException {
        public BlankCategoryNameException(String message) {
            super(message);
        }
    }

    public static class CategoryWithTheSameNameException extends RuntimeException {
        public CategoryWithTheSameNameException(String message) {
            super(message);
        }
    }

    public static class CategoryNotExists extends RuntimeException {
        public CategoryNotExists(String message) {
            super(message);
        }
    }

    public static class ProductAlreadyExistsInThisCategory extends RuntimeException {
        public ProductAlreadyExistsInThisCategory(String message) {
            super(message);
        }
    }

    public static class ProductNotExistsInThisCategory extends RuntimeException {
        public ProductNotExistsInThisCategory(String message) {
            super(message);
        }
    }

    @Autowired
    ShelfRepository shelfRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    public Category createNewCategory(MyUser currentUser, Integer shelfId, String name) {
        if(name == null || name.isBlank()) {
            throw new ShelfService.BlankShelfNameException("Blank shelf name");
        }
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Optional<Shelf> optionalShelf = shelfRepository.findById(shelfId);
        if(optionalShelf.isEmpty()) {
            throw new ShelfService.ShelfNotExists("Shelf not exist");
        }
        Shelf shelf = optionalShelf.get();
        if(shelf.getOwner() != currentUser) {
            throw new MyUserService.UserLackPermission("Not user shelf");
        }
        Optional<Category> byShelfAndName = categoryRepository.findByShelfAndName(shelf, name);
        if(byShelfAndName.isPresent()) {
            throw new CategoryWithTheSameNameException("Category with the same name exists");
        }
        Category category = Category.builder()
                .name(name)
                .shelf(shelf)
                .build();
        return categoryRepository.save(category);
    }

    public Category renameCategory(MyUser currentUser, Integer categoryId, String newName) {
        if(newName == null || newName.isBlank()) {
            throw new BlankCategoryNameException("Blank category name");
        }
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Category category = getUserCategory(currentUser, categoryId);
        Optional<Category> byShelfAndName = categoryRepository.findByShelfAndName(category.getShelf(), newName);
        if(byShelfAndName.isPresent()) {
            throw new CategoryWithTheSameNameException("Category with the same name exists");
        }
        category.setName(newName);
        return categoryRepository.save(category);
    }

    public void deleteCategory(MyUser currentUser, Integer categoryId) {
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Category category = getUserCategory(currentUser, categoryId);
        categoryRepository.delete(category);
    }

    public CategoryProduct addNewProductToCategory(MyUser currentUser, Integer categoryId, String productName, Integer amount) {
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Category category = getUserCategory(currentUser, categoryId);
        Product product = productService.getOrCreateProduct(productName);
        Optional<CategoryProduct> optionalCategoryProduct = category.findCategoryProduct(product);
        if(optionalCategoryProduct.isPresent()) {
            throw new ProductAlreadyExistsInThisCategory("Product already exists in this category");
        }
        category.addNewProduct(product,amount);
        category = categoryRepository.save(category);
        optionalCategoryProduct = category.findCategoryProduct(product);
        if(optionalCategoryProduct.isEmpty()) {
            throw new ProductNotExistsInThisCategory("Something went wrong");
        }
        return optionalCategoryProduct.get();
    }

    public void deleteCategoryProduct(MyUser currentUser, Integer categoryId, Integer productId) {
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Category category = getUserCategory(currentUser, categoryId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()) {
            throw new ProductService.ProductNotExists("Product not exists");
        }
        Product product = optionalProduct.get();
        Optional<CategoryProduct> optionalCategoryProduct = category.findCategoryProduct(product);
        if(optionalCategoryProduct.isEmpty()) {
            throw new ProductNotExistsInThisCategory("Product not exists in this category");
        }
        category.removeProduct(product);
        categoryRepository.save(category);
    }

    public CategoryProduct changeAmountOfCategoryProduct(MyUser currentUser, Integer categoryId, Integer productId, Integer delta) {
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Category category = getUserCategory(currentUser, categoryId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()) {
            throw new ProductService.ProductNotExists("Product not exists");
        }
        Product product = optionalProduct.get();
        Optional<CategoryProduct> optionalCategoryProduct = category.findCategoryProduct(product);
        if(optionalCategoryProduct.isEmpty()) {
            throw new ProductNotExistsInThisCategory("Product not exists in this category");
        }
        CategoryProduct categoryProduct = optionalCategoryProduct.get();
        Integer amount = categoryProduct.getAmount();
        amount += delta;
        if(amount < 0) {
            amount = 0;
        }
        categoryProduct.setAmount(amount);
        categoryRepository.save(category);
        optionalCategoryProduct = category.findCategoryProduct(product);
        if(optionalCategoryProduct.isEmpty()) {
            throw new ProductNotExistsInThisCategory("Something went wrong");
        }
        return optionalCategoryProduct.get();
    }

    private Category getUserCategory(MyUser currentUser, Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isEmpty()) {
            throw new CategoryNotExists("Category not exists");
        }
        Category category = optionalCategory.get();
        if(category.getShelf() == null || category.getShelf().getOwner() != currentUser) {
            throw new MyUserService.UserLackPermission("Not users shelf");
        }
        return category;
    }


}
