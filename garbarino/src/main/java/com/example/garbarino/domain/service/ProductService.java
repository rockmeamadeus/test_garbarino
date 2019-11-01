package com.example.garbarino.domain.service;


import com.example.garbarino.domain.model.Product;
import reactor.core.publisher.Mono;

public interface ProductService {


    Mono<Product> createProduct(Product product);
}
