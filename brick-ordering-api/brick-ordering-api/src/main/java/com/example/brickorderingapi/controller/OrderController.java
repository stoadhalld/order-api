package com.example.brickorderingapi.controller;

import com.example.brickorderingapi.model.Order;
import com.example.brickorderingapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Sam Doran
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * Used to return all Order objects stored for every customer
     * @return List This returns a list of all Order Objects.
     */
    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders(){

        List<Order> allOrders = orderService.getAllOrders();

        if(!allOrders.isEmpty()){
            return ResponseEntity.ok(allOrders);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Used to return all Order objects stored for a specified customer reference
     * @param id Customer reference number
     * @return List This returns a list of all Order Objects relating to the customer.
     */
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<Order>> getAllOrdersForCustomer(@PathVariable String id){

        List orders = orderService.getAllOrdersForCustomer(id);

        if(!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Used to return a single specified Order object
     * @param id Order reference number
     * @return Order This returns the Order relating to the id provided
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id){

        Optional<Order> order = orderService.getOrderById(id);

        if(order.isPresent()){
                    return ResponseEntity.ok(order.get());
        } else {
                    return ResponseEntity.notFound().build();
        }
    }

    /**
     * Used to mark an Order object as dispatched
     * @param id Order reference number
     * @return Order This returns the Order relating to the id provided
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Order> markOrderAsDispatched(@PathVariable String id){

        Optional<Order> order = orderService.markAsDispatched(id);

        if(order.isPresent()){
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Used to edit an Order object
     * @param id Order reference number
     * @param quantityOfBricks the new quantity of bricks
     * @return String This returns the order reference of the edited Order.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> editOrderById(@PathVariable String id, @RequestParam int quantityOfBricks){

        Optional<Order> order = orderService.editOrderById(id, quantityOfBricks);

        if(order.isPresent()){
            if(!order.get().isDispatched()){
                return ResponseEntity.ok(order.get().getOrderReference());
            }
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Used to create an Order object
     * @param customerReference customer reference number
     * @param quantityOfBricks the quantity of bricks
     * @return String This returns the order reference of the created Order.
     */
    @PostMapping("")
    public ResponseEntity<String> createOrder(@RequestParam String customerReference, @RequestParam int quantityOfBricks){

        String OrderReference = orderService.createOrder(customerReference, quantityOfBricks);

        return ResponseEntity.status(201).body(OrderReference);

    }



}
