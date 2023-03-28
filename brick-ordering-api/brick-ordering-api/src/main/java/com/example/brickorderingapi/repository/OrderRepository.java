package com.example.brickorderingapi.repository;

import com.example.brickorderingapi.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    @Query("{customerReference : ?0}")
    List<Order> getOrdersByCustomerReference(String customerReference);

}
