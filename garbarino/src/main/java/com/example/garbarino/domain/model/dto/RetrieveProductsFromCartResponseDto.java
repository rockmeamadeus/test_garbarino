package com.example.garbarino.domain.model.dto;

import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.Item;
import com.example.garbarino.domain.model.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RetrieveProductsFromCartResponseDto {

    private List<Product> products;

    public RetrieveProductsFromCartResponseDto(Cart cart) {

        products = cart.getItems().stream().map(Item::getProduct).collect(Collectors.toList());
    }
}
