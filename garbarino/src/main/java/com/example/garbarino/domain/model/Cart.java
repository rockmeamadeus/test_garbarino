package com.example.garbarino.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Document(collection = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    private String id;

    private String fullName;

    private String email;

    private LocalDateTime creationDate = LocalDateTime.now();

    @DBRef
    private List<Item> items = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    private Status status = Status.NEW;


    public void addItem(Item item1) {
        items.add(item1);
        total = total.add(item1.getTotal());
    }

    public void removeItem(String productId) {

        Optional<Item> item = items.stream()
                .filter(i -> i.getProduct().getId().equals(productId)).findFirst();

        if (item.isPresent()) {
            total = total.subtract(item.get().getTotal());
            items.removeIf(i -> i.getProduct().getId().equals(item.get().getProduct().getId()));
        }
    }

    public boolean itemAlreadyExist(Item item) {
        return items.stream()
                .anyMatch(i -> i.getProduct().getId().equals(item.getProduct().getId()));
    }


}
