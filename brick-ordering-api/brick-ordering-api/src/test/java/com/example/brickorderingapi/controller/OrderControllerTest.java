package com.example.brickorderingapi.controller;


import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void createOrder_OK() throws Exception {
        mockMvc.perform(post("/order?customerReference=test2&quantityOfBricks=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", 	hasLength(24)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void editOrderById_OK() throws Exception {
        mockMvc.perform(put("/order/6422bcc593ac02074642a734?quantityOfBricks=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", 	hasLength(24)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void editOrderById_NOTFOUND() throws Exception {
        mockMvc.perform(put("/order/xxx?quantityOfBricks=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void editOrderById_BADREQUEST() throws Exception {
        mockMvc.perform(put("/order/6422a04647a99f15b12ad7a1?quantityOfBricks=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllOrders_OK() throws Exception {
        mockMvc.perform(get("/order").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(1))))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOrdersForCustomer_OK() throws Exception {
        mockMvc.perform(get("/order/customer/test").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllOrdersForCustomer_NOTFOUND() throws Exception {
        mockMvc.perform(get("/order/customer/xxx").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOrderById_OK() throws Exception {
        mockMvc.perform(get("/order/6422a04647a99f15b12ad7a1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrderById_NOTFOUND() throws Exception {
        mockMvc.perform(get("/order/xxx").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void markOrderAsDispatched_OK() throws Exception {
        mockMvc.perform(patch("/order/6422a04647a99f15b12ad7a1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.dispatched", is(true)))
                .andExpect(status().isOk());
    }

    @Test
    public void markOrderAsDispatched_BADREQUEST() throws Exception {
        mockMvc.perform(patch("/order/xxx").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}