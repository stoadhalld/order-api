package com.example.brickorderingapi.service;

import com.example.brickorderingapi.model.Order;
import com.example.brickorderingapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Optional<Order> getOrderById(String id){

        Optional<Order> order = this.orderRepository.findById(id);

        return order;
    }

    public List<Order> getAllOrders(){

        List<Order> allOrders = this.orderRepository.findAll();

        return allOrders;
    }

    public List<Order> getAllOrdersForCustomer(String customerReference){

        List<Order> customerOrders = this.orderRepository.getOrdersByCustomerReference(customerReference);

        return customerOrders;
    }

    public String createOrder(String customerReference, int quantityOfBricks){

        Order singleOrder = new Order(customerReference, quantityOfBricks);

        this.orderRepository.save(singleOrder);

        return singleOrder.getOrderReference();
    }

    public Optional<Order> markAsDispatched(String id){

        Optional<Order> order = this.orderRepository.findById(id);

        if(order.isPresent()) {
            order.get().setDispatched(true);
            this.orderRepository.save(order.get());
        }
            return order;
        }

    public Optional<Order> editOrderById(String id, int quantityOfBricks){

        Optional<Order> order = this.orderRepository.findById(id);

        if(order.isPresent()) {

            if(!order.get().isDispatched()){
                order.get().setQuantityOfBricks(quantityOfBricks);
                this.orderRepository.save(order.get());
            }
        }

        return order;
    }


}
