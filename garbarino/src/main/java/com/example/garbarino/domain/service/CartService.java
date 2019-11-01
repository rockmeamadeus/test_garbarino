package com.example.garbarino.domain.service;


import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.dto.ProductDto;
import reactor.core.publisher.Mono;

public interface CartService {

    Mono<Cart> createCart(Cart cart);

    Mono<Cart> addProduct(String cartId, ProductDto productDto);

    Mono<Cart> removeProduct(String id, String productId);

    Mono<Cart> findById(String id);

    Mono<Cart> checkout(String id);
}
