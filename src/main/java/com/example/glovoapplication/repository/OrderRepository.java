package com.example.glovoapplication.repository;

import com.example.glovoapplication.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
    public interface OrderRepository extends CrudRepository<Order, Long> {

}




