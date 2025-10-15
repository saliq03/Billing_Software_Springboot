package com.saliqdar.billingsoftware.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.saliqdar.billingsoftware.io.RazorpayOrderResponse;
import com.saliqdar.billingsoftware.service.RazorpayService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

public class RazorpayServiceImpl implements RazorpayService {
    @Value("$razorpay.key.id")
    private String razorpayKeyId;
    @Value("$razorpay.key.secret")
    private String razorpaySecretKey;
    @Override
    public RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpaySecretKey,razorpaySecretKey);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",amount*1000);
        orderRequest.put("currency",currency);
        orderRequest.put("receipt","order_rcptid "+System.currentTimeMillis());
        orderRequest.put("payment_capture",1);
        Order order=razorpayClient.orders.create(orderRequest);
        return convertToResponse(order);
    }

    private RazorpayOrderResponse convertToResponse(Order order) {
        return RazorpayOrderResponse.builder()
                .id(order.get("id"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .receipt(order.get("receipt"))
                .createdAt(order.get("created_at"))
                .status(order.get("status"))
                .build();
    }
}
