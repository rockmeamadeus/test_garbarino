package com.example.garbarino.domain.persistence;

import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends ReactiveMongoRepository<Cart, String> {


}
