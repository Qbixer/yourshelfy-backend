package com.rysiki.yourshelfy.shelf.entity;

import com.rysiki.yourshelfy.product.entity.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "category_product")
public class CategoryProduct {

    @EmbeddedId
    CategoryProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    Product product;

    @Column
    Integer amount;
}
