package com.example.garbarino.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    private String description;

    private BigDecimal unitPrice;

    private Integer stock;

}
