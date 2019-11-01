package com.example.garbarino.domain.persistence;

import com.example.garbarino.domain.model.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends ReactiveMongoRepository<Item, String> {


}
