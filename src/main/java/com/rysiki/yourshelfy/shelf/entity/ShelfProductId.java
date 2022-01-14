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
public class ShelfProductId implements Serializable {

    @Column(name = "shelf_id")
    Integer shelfId;

    @Column(name = "product_id")
    Integer productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ShelfProductId that = (ShelfProductId) o;
        return Objects.equals(shelfId, that.shelfId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelfId, productId);
    }
}
