package com.rysiki.yourshelfy.product.entity;

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
}
