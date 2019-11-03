package com.example.garbarino.domain.service.impl;

import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.Item;
import com.example.garbarino.domain.model.Product;
import com.example.garbarino.domain.model.Status;
import com.example.garbarino.domain.model.dto.addProduct.ProductDto;
import com.example.garbarino.domain.persistence.CartRepository;
import com.example.garbarino.domain.persistence.LineItemRepository;
import com.example.garbarino.domain.persistence.ProductRepository;
import com.example.garbarino.domain.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Override
    public Mono<Cart> createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Mono<Cart> addProduct(String cartId, ProductDto productDto) {

        Mono<Product> productMono = productRepository.
                findById(productDto.getId()).switchIfEmpty(Mono.error(new Exception("No Product found with Id: " + productDto.getId())));
        Mono<Cart> cartMono = cartRepository.findById(cartId).switchIfEmpty(Mono.error(new Exception("No Cart found with Id: " + cartId)));

       return cartMono.
                flatMap(cart -> cart.itemAlreadyExist(productDto.getId()) ?
                        Mono.error(new Exception("The product already exist, we can not go any further")) :
                        Mono.just(cart)).flatMap(cart -> productMono.map(product -> Item.builder().
                unitPrice(product.getUnitPrice()).
                quantity(productDto.getQuantity()).
                product(product).build()).
                flatMap(item -> lineItemRepository.save(item)).
                flatMap(item -> cartMono.flatMap(cartToUpdate -> {
                    cartToUpdate.addItem(item);
                    return cartRepository.save(cartToUpdate);
                })));
    }

    @Override
    public Mono<Cart> removeProduct(String id, String productId) {

        Mono<Cart> cartMono = cartRepository.findById(id).switchIfEmpty(Mono.error(new Exception("No Cart found with Id: " + id)));

        return cartMono.flatMap(cart -> {

            cart.removeItem(productId);

            return cartRepository.save(cart);
        });
    }

    @Override
    public Mono<Cart> findById(String id) {
        return cartRepository.findById(id);
    }

    @Override
    public Mono<Cart> checkout(String id) {
        Mono<Cart> cartMono = cartRepository.findById(id).switchIfEmpty(Mono.error(new Exception("No Cart found with Id: " + id)));

        return cartMono.flatMap(cart -> {

            if (cart.getItems().size() == 0) {
                return Mono.error(new IllegalArgumentException("Cart is empty, we can not go any further"));
            }
            cart.setStatus(Status.READY);

            return cartRepository.save(cart);
        });
    }
}
