package com.rysiki.yourshelfy.product.entity;

import com.rysiki.yourshelfy.commons.entity.Image;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "product_id_seq")
    Integer id;

    @Column(unique = true)
    String name;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id")
    Image icon;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    ProductCategory category;
}
