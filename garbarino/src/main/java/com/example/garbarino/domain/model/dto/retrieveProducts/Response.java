package com.example.garbarino.domain.model.dto.retrieveProducts;

import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.Item;
import com.example.garbarino.domain.model.Product;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Response {

    private List<ProductDto> products;

    public Response(Cart cart) {

        products = cart.getItems().stream().
                map(item ->
                        ProductDto.builder().
                                unitPrice(item.getUnitPrice()).
                                description(item.getProduct().getDescription()).
                                id(item.getProduct().getId()).
                                quantity(item.getQuantity()).
                                build()).collect(Collectors.toList());
    }
}
