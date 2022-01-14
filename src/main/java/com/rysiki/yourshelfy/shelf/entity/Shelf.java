package com.rysiki.yourshelfy.shelf.entity;

import com.rysiki.yourshelfy.commons.entity.Image;
import com.rysiki.yourshelfy.product.entity.Product;
import com.rysiki.yourshelfy.product.entity.ProductCategory;
import lombok.*;

import javax.persistence.*;
import java.util.Iterator;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "shelf")
public class Shelf {

    @Id
    @GeneratedValue(generator = "shelf_id_seq")
    Integer id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Integer ownerId;

    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "shelf",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    Set<ShelfProduct> products;

    public void addNewProduct(Product product, Integer count) {
        products.add(ShelfProduct.builder()
                .shelf(this)
                .product(product)
                .count(count)
                .build());
    }

    public void removeProduct(Product product) {
        for (Iterator<ShelfProduct> iterator = products.iterator();
             iterator.hasNext(); ) {
            ShelfProduct shelfProduct = iterator.next();

            if (shelfProduct.getShelf().equals(this) &&
                    shelfProduct.getProduct().equals(product)) {
                iterator.remove();
                shelfProduct.setProduct(null);
                shelfProduct.setShelf(null);
            }
        }
    }
}
