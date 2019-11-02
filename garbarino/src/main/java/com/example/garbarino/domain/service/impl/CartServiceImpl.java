package com.example.garbarino.domain.service.impl;

import com.example.garbarino.domain.model.Cart;
import com.example.garbarino.domain.model.Item;
import com.example.garbarino.domain.model.Product;
import com.example.garbarino.domain.model.Status;
import com.example.garbarino.domain.model.dto.ProductDto;
import com.example.garbarino.domain.persistence.CartRepository;
import com.example.garbarino.domain.persistence.LineItemRepository;
import com.example.garbarino.domain.persistence.ProductRepository;
import com.example.garbarino.domain.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.stream.Collectors;

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

        Mono<Product> productMono = productRepository.findById(productDto.getId());
        Mono<Cart> cartMono = cartRepository.findById(cartId);

        return productMono.flatMap(product -> {

            Item item = Item.builder().
                    price(product.getUnitPrice()).
                    quantity(productDto.getQuantity())
                    .product(product).build();

            return lineItemRepository.save(item).flatMap(item1 ->
                    cartMono.flatMap(cart -> {
                        if (cart.itemAlreadyExist(item1)) {
                            return Mono.error(new IllegalArgumentException("The product already exist, we can not go any further"));
                        }
                        cart.addItem(item1);
                        return cartRepository.save(cart);
                    }));
        });
    }

    @Override
    public Mono<Cart> removeProduct(String id, String productId) {

        Mono<Cart> cartMono = cartRepository.findById(id);

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
        Mono<Cart> cartMono = cartRepository.findById(id);

        return cartMono.flatMap(cart -> {

            if (cart.getItems().size() == 0) {
                return Mono.error(new IllegalArgumentException("Cart is empty, we can not go any further"));
            }
            cart.setStatus(Status.READY);

            return cartRepository.save(cart);
        });
    }
}
