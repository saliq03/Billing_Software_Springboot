package com.saliqdar.billingsoftware.controller;

import com.saliqdar.billingsoftware.io.OrderRequest;
import com.saliqdar.billingsoftware.io.OrderResponse;
import com.saliqdar.billingsoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest){
       return orderService.createOrder(orderRequest);
    }
    @GetMapping("/latest")
    public List<OrderResponse> getLatestOrders(){
        return orderService.getLatestOrders();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable String orderId){
         orderService.deleteOrder(orderId);
    }

}
