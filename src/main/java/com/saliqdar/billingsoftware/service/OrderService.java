package com.saliqdar.billingsoftware.service;

import com.saliqdar.billingsoftware.io.OrderRequest;
import com.saliqdar.billingsoftware.io.OrderResponse;
import com.saliqdar.billingsoftware.io.PaymentVerificationRequest;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);
    void deleteOrder(String orderId);
    List<OrderResponse> getLatestOrders();
    OrderResponse verifyPayment(PaymentVerificationRequest paymentVerificationRequest);


}
