package com.example.garbarino.domain.model.dto.retrieveProducts;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDto {

    private String id;

    private String description;

    private BigDecimal unitPrice;

    private Integer quantity;
}
