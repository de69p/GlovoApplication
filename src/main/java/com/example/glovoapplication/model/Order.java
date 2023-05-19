package com.example.glovoapplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private Long id;

    public Order(Long id, LocalDate date, Double cost) {
        this.id = id;
        this.date = date;
        this.cost = cost;
    }

    public Order(Long id, Double cost) {
        this.id = id;
        this.cost = cost;
    }

    private LocalDate date;
    private Double cost;
    @OneToMany
    private List<Product> products;

}
