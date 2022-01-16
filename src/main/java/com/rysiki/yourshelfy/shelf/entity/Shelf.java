package com.rysiki.yourshelfy.shelf.entity;

import com.rysiki.yourshelfy.auth.entity.MyUser;
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
@Table(name = "shelf")
public class Shelf {

    @Id
    @GeneratedValue(generator = "shelf_id_seq")
    Integer id;

    @Column
    String name;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_email")
    MyUser owner;

    @Column(name = "is_shopping_list")
    Boolean isShoppingList;

    @Column(name = "is_null_shelf")
    Boolean isNullShelf;

    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shelf", cascade = {CascadeType.ALL})
    Set<Category> categories;

    //TODO share shelf
}
