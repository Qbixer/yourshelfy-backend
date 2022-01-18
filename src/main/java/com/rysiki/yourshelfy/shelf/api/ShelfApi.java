package com.rysiki.yourshelfy.shelf.api;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.auth.service.MyUserService;
import com.rysiki.yourshelfy.shelf.dto.ShelfDTO;
import com.rysiki.yourshelfy.shelf.entity.Shelf;
import com.rysiki.yourshelfy.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    Set<ShelfDTO> getAllUserShelves() {
        MyUser currentUser = myUserService.getCurrentUser();
        Set<Shelf> shelves = shelfService.getAllUserShelves(currentUser);
        return shelves.stream().map(ShelfDTO::createShelfDTOFromShelf).collect(Collectors.toSet());
    }

    @PostMapping("create")
    ShelfDTO createNewShelf(@RequestBody String name) {
        MyUser currentUser = myUserService.getCurrentUser();
        Shelf newShelf = shelfService.createNewShelf(currentUser, name);
        return ShelfDTO.createShelfDTOFromShelf(newShelf);
    }

    @PutMapping("rename/{id}")
    ShelfDTO renameShelf(@PathVariable Integer id, @RequestBody String name) {
        MyUser currentUser = myUserService.getCurrentUser();
        Shelf shelf = shelfService.renameShelf(currentUser, id, name);
        return ShelfDTO.createShelfDTOFromShelf(shelf);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity deleteShelf(@PathVariable Integer id) {
        MyUser currentUser = myUserService.getCurrentUser();
        shelfService.deleteShelf(currentUser, id);
        return ResponseEntity.ok().build();
    }




}
