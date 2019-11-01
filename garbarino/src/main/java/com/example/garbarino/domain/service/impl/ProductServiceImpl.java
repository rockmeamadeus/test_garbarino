package com.example.garbarino.domain.service.impl;

import com.example.garbarino.domain.model.Product;
import com.example.garbarino.domain.persistence.ProductRepository;
import com.example.garbarino.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.insert(product);
    }
}
