package com.rysiki.yourshelfy.shelf.entity;

import com.rysiki.yourshelfy.product.entity.Product;
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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(generator = "category_id_seq")
    Integer id;

    @Column
    String name;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id")
    Shelf shelf;

    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "category",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    Set<CategoryProduct> products;

    public void addNewProduct(Product product, Integer amount) {
        products.add(CategoryProduct.builder()
                .category(this)
                .product(product)
                .amount(amount)
                .build());
    }

    public void removeProduct(Product product) {
        for (Iterator<CategoryProduct> iterator = products.iterator();
             iterator.hasNext(); ) {
            CategoryProduct categoryProduct = iterator.next();

            if (categoryProduct.getCategory().equals(this) &&
                    categoryProduct.getProduct().equals(product)) {
                iterator.remove();
                categoryProduct.setProduct(null);
                categoryProduct.setCategory(null);
            }
        }
    }
}
