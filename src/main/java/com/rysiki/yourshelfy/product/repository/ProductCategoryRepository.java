package com.rysiki.yourshelfy.product.repository;

import com.rysiki.yourshelfy.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,String> {


}
