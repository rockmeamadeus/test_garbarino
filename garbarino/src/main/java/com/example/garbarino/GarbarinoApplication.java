package com.example.garbarino;

import com.example.garbarino.domain.model.Product;
import com.example.garbarino.domain.persistence.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Log4j2
@EnableMongoAuditing
@EnableReactiveMongoRepositories
@SpringBootApplication
public class GarbarinoApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(GarbarinoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        productRepository.deleteAll().subscribe();

        Product product1 = Product.builder().description("TV 32").stock(150).unitPrice(BigDecimal.valueOf(32.43)).build();
        Product product2 = Product.builder().description("TV 40").stock(100).unitPrice(BigDecimal.valueOf(60.43)).build();
        Product product3 = Product.builder().description("TV 55").stock(199).unitPrice(BigDecimal.valueOf(85.43)).build();

        productRepository.saveAll(Flux.just(product1, product2, product3)).subscribe(product -> log.info("saving " + product));


    }
}
