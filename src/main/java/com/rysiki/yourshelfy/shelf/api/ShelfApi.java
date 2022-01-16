package com.rysiki.yourshelfy.shelf.api;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import com.rysiki.yourshelfy.shelf.dto.ShelfDTO;
import com.rysiki.yourshelfy.shelf.entity.Shelf;
import com.rysiki.yourshelfy.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RequestMapping("/shelf")
@RestController
public class ShelfApi {


    @Autowired
    ShelfService shelfService;

    @Autowired
    MyUserService myUserService;

    @GetMapping("")
    ResponseEntity getAllUserShelves() {
        try {
            MyUser currentUser = myUserService.getCurrentUser();
            Set<Shelf> shelves = shelfService.getAllUserShelves(currentUser);
            return ResponseEntity.ok(shelves.stream().map(ShelfDTO::createShelfDTOFromShelf).collect(Collectors.toSet()));
        } catch (MyUserService.UserNotFound | MyUserService.UserNotAuthenticated e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("create")
    ResponseEntity createNewShelf(@RequestBody String name) {
        try {
            MyUser currentUser = myUserService.getCurrentUser();
            Shelf newShelf = shelfService.createNewShelf(currentUser, name);
            return ResponseEntity.ok(ShelfDTO.createShelfDTOFromShelf(newShelf));
        } catch (MyUserService.UserNotFound | MyUserService.UserNotAuthenticated | ShelfService.BlankShelfNameException | ShelfService.ShelfWithTheSameNameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("rename/{id}")
    ResponseEntity renameShelf(@PathVariable Integer id, @RequestBody String name) {
        try {
            MyUser currentUser = myUserService.getCurrentUser();
            Shelf shelf = shelfService.renameShelf(currentUser, id, name);
            return ResponseEntity.ok(ShelfDTO.createShelfDTOFromShelf(shelf));
        } catch (MyUserService.UserNotFound | MyUserService.UserNotAuthenticated | ShelfService.ShelfNotExists | ShelfService.BlankShelfNameException | MyUserService.UserLackPermission e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity deleteShelf(@PathVariable Integer id) {
        try {
            MyUser currentUser = myUserService.getCurrentUser();
            shelfService.deleteShelf(currentUser, id);
            return ResponseEntity.ok().build();
        } catch (MyUserService.UserNotFound | MyUserService.UserNotAuthenticated | ShelfService.ShelfNotExists | MyUserService.UserLackPermission e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
