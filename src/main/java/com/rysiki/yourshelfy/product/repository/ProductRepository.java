package com.rysiki.yourshelfy.product.repository;

import com.rysiki.yourshelfy.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {


}
