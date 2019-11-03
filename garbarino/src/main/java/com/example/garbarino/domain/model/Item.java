package com.example.garbarino.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Document(collection = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    private String id;

    private Integer quantity;

    @DBRef
    private Product product;

    public BigDecimal getTotal() {
        return product.getUnitPrice().multiply(new BigDecimal(quantity));
    }
}
