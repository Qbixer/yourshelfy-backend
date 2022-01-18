package com.rysiki.yourshelfy.shelf.service;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import com.rysiki.yourshelfy.shelf.dto.CategoryDTO;
import com.rysiki.yourshelfy.shelf.entity.Category;
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

    @Autowired
    ShelfRepository shelfRepository;

    @Autowired
    CategoryRepository categoryRepository;

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
