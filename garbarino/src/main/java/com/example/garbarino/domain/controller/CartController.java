package com.example.garbarino.domain.controller;


import com.example.garbarino.domain.model.dto.getCart.Response;
import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.dto.addProduct.ProductDto;
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
    public Mono<ResponseEntity<Cart>> createCart(@RequestBody Cart cart) {
        log.info("Entering to create...");
        return cartService.createCart(cart).map(ResponseEntity::ok);
    }

    @PutMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Void>> addProductToCart(@PathVariable("id") String id, @RequestBody ProductDto productDto) {
        log.info("Entering to addProduct...");
        return cartService.addProduct(id, productDto).flatMap(cart -> Mono.empty());
    }

    @PutMapping("/{id}/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Void>> removeProductFromCart(@PathVariable("id") String id, @PathVariable("productId") String productId) {
        log.info("Entering to removeProduct...");
        return cartService.removeProduct(id, productId).flatMap(cart -> Mono.empty());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Response>> retrieveCart(@PathVariable("id") String id) {
        log.info("Entering to retrieveCart...");
        return cartService.findById(id).map(Response::new).map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<com.example.garbarino.domain.model.dto.retrieveProducts.Response>> retrieveProductsFromCart(@PathVariable("id") String id) {
        log.info("Entering to retrieveProductFromCart...");
        return cartService.findById(id).map(com.example.garbarino.domain.model.dto.retrieveProducts.Response::new).map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}/checkout")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Void>> checkout(@PathVariable("id") String id) {
        log.info("Entering to checkout...");
        return cartService.checkout(id).flatMap(cart -> Mono.empty());
    }

}
