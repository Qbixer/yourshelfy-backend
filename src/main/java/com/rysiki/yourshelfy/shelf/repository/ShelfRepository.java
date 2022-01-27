package com.rysiki.yourshelfy.shelf.repository;

import com.rysiki.yourshelfy.auth.entity.MyUser;
import com.rysiki.yourshelfy.product.entity.Product;
import com.rysiki.yourshelfy.shelf.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ShelfRepository extends JpaRepository<Shelf,Integer> {

    Set<Shelf> findAllByOwner(MyUser myUser);

    Optional<Shelf> findByOwnerAndName(MyUser myUser, String name);

    Optional<Shelf> findByOwnerAndIsShoppingList(MyUser myUser, Boolean isShoppingList);
}
