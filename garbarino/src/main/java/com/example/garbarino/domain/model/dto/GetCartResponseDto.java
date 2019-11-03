package com.example.garbarino.domain.model.dto;

import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.Item;
import com.example.garbarino.domain.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetCartResponseDto {

    private BigDecimal total;

    private List<String> productIds = new ArrayList();

    public GetCartResponseDto(Cart cart) {

        total = cart.getTotal();

        productIds.addAll(cart.getItems().stream().map(Item::getProduct).map(Product::getId).collect(Collectors.toList()));
    }
}
