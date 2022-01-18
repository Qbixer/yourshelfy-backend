package com.rysiki.yourshelfy.shelf.service;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import com.rysiki.yourshelfy.shelf.entity.Shelf;
import com.rysiki.yourshelfy.shelf.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ShelfService {

    public static class BlankShelfNameException extends RuntimeException {
        public BlankShelfNameException(String message) {
            super(message);
        }
    }

    public static class ShelfWithTheSameNameException extends RuntimeException {
        public ShelfWithTheSameNameException(String message) {
            super(message);
        }
    }

    public static class ShelfNotExists extends RuntimeException {
        public ShelfNotExists(String message) {
            super(message);
        }
    }

    public static class DefaultShelfException extends RuntimeException {
        public DefaultShelfException(String message) {
            super(message);
        }
    }

    @Autowired
    ShelfRepository shelfRepository;

    public Set<Shelf> getAllUserShelves(MyUser currentUser) {
        return shelfRepository.findAllByOwner(currentUser);
    }

    public void createNullAndCartShelfForNewUser(MyUser currentUser) {
        shelfRepository.save(Shelf.builder()
                .owner(currentUser)
                .isShoppingList(false)
                .isNullShelf(true)
                .build());
        shelfRepository.save(Shelf.builder()
                .owner(currentUser)
                .isShoppingList(true)
                .isNullShelf(false)
                .build());
    }

    public Shelf createNewShelf(MyUser currentUser, String name) {
        if(name == null || name.isBlank()) {
            throw new BlankShelfNameException("Blank shelf name");
        }
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Optional<Shelf> optionalShelf = shelfRepository.findByOwnerAndName(currentUser, name);
        if(optionalShelf.isPresent()) {
            throw new ShelfWithTheSameNameException("Shelf with this name already exist for this user");
        }
        return shelfRepository.save(Shelf.builder()
                .name(name)
                .owner(currentUser)
                .isNullShelf(false)
                .isShoppingList(false)
                .build());
    }

    public Shelf renameShelf(MyUser currentUser, Integer shelfId, String newName) {
        if(newName == null || newName.isBlank()) {
            throw new BlankShelfNameException("Blank shelf name");
        }
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Optional<Shelf> optionalShelf = shelfRepository.findByOwnerAndName(currentUser, newName);
        if(optionalShelf.isPresent()) {
            throw new ShelfWithTheSameNameException("Shelf with this name already exist for this user");
        }
        Shelf shelf = getUserShelf(currentUser, shelfId);
        shelf.setName(newName);
        return shelfRepository.save(shelf);
    }

    public void deleteShelf(MyUser currentUser, Integer shelfId) {
        if (currentUser == null) {
            throw new MyUserService.UserNotAuthenticated("User not authenticated");
        }
        Shelf shelf = getUserShelf(currentUser, shelfId);
        if(shelf.getIsNullShelf() || shelf.getIsShoppingList()) {
            throw new DefaultShelfException("Trying to delete default shelf");
        }
        shelfRepository.delete(shelf);
    }

    private Shelf getUserShelf(MyUser currentUser, Integer shelfId) {
        Optional<Shelf> optionalShelf = shelfRepository.findById(shelfId);
        if(optionalShelf.isEmpty()) {
            throw new ShelfNotExists("Shelf not exists");
        }
        Shelf shelf = optionalShelf.get();
        if(shelf.getOwner() != currentUser) {
            throw new MyUserService.UserLackPermission("Not users shelf");
        }
        return shelf;
    }
}