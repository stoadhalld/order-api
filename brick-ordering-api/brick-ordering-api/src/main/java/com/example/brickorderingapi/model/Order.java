package com.example.brickorderingapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("orders")
public class Order {

    @Id
    private String orderReference;

    private String customerReference;

    private int quantityOfBricks;

    private boolean dispatched;

    public Order(String customerReference, int quantityOfBricks){

      this.customerReference = customerReference;
      this.quantityOfBricks = quantityOfBricks;

    }
}
