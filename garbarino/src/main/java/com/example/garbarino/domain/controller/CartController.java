package com.example.garbarino.domain.controller;


import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.dto.ProductDto;
import com.example.garbarino.domain.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cart> create(@RequestBody Cart cart) {
        log.debug("create Cart with cart : {}", cart);
        return cartService.createCart(cart);
    }

    @PutMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Cart>> addProduct(@PathVariable("id") String id, @RequestBody ProductDto productDto) {
        log.info("Entering to addProduct...");
        return cartService.addProduct(id, productDto).map(cart -> ResponseEntity.ok(cart))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Cart>> removeProduct(@PathVariable("id") String id, @PathVariable("productId") String productId) {
        log.info("Entering to removeProduct...");
        return cartService.removeProduct(id, productId).map(cart -> ResponseEntity.ok(cart))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Cart>> retrieveCart(@PathVariable("id") String id) {
        log.info("Entering to removeProduct...");
        return cartService.findById(id).map(cart -> ResponseEntity.ok(cart))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/checkout")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Cart>> checkout(@PathVariable("id") String id) {
        log.info("Entering to addProduct...");
        return cartService.checkout(id).map(cart -> ResponseEntity.ok(cart))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
