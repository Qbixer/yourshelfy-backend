package com.rysiki.yourshelfy.shelf.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CategoryProductId implements Serializable {

    @Column(name = "category_id")
    Integer categoryId;

    @Column(name = "product_id")
    Integer productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CategoryProductId that = (CategoryProductId) o;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, productId);
    }
}
