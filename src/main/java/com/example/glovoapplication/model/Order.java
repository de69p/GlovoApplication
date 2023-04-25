package com.example.glovoapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {

    @Id
    private Long id;
    private Date date;
    private Double cost;
    @OneToMany
    private List<Product> products;

    public Order(Long id, Date date, Double cost) {
        this.id = id;
        this.date = date;
        this.cost = cost;
    }


    public void addProduct(Product product) {
        products.add(product);
    }
}
