package com.saliqdar.billingsoftware.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.saliqdar.billingsoftware.io.PaymentRequest;
import com.saliqdar.billingsoftware.io.RazorpayOrderResponse;
import com.saliqdar.billingsoftware.service.RazorpayService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayServiceImpl implements RazorpayService {
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;
    @Override
    public RazorpayOrderResponse createOrder(PaymentRequest paymentRequest) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpayKeySecret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",paymentRequest.getAmount()*1000);
        orderRequest.put("currency",paymentRequest.getCurrency());
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
                .entity(order.get("entity"))
                .createdAt(order.get("created_at"))
                .status(order.get("status"))
                .build();
    }
}
