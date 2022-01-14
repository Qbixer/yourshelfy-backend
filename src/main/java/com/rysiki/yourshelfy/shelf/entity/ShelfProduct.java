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
@Table(name = "shelf_product")
public class ShelfProduct {

    @EmbeddedId
    ShelfProductId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("shelfId")
    Shelf shelf;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    Product product;

    @Column
    Integer count;
}
