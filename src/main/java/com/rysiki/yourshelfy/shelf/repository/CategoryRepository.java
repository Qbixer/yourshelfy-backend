package com.rysiki.yourshelfy.shelf.repository;

import com.rysiki.yourshelfy.shelf.entity.Category;
import com.rysiki.yourshelfy.shelf.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findByShelfAndName(Shelf shelf, String name);
}
