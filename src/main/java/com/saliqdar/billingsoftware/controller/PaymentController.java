package com.saliqdar.billingsoftware.controller;

import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import com.saliqdar.billingsoftware.io.OrderResponse;
import com.saliqdar.billingsoftware.io.PaymentRequest;
import com.saliqdar.billingsoftware.io.PaymentVerificationRequest;
import com.saliqdar.billingsoftware.io.RazorpayOrderResponse;
import com.saliqdar.billingsoftware.service.OrderService;
import com.saliqdar.billingsoftware.service.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private  final RazorpayService razorpayService;
    private  final OrderService orderService;

    @PostMapping("/create_order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createPayment(@RequestBody PaymentRequest paymentRequest) throws RazorpayException {
        return razorpayService.createOrder(paymentRequest);
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request) {
        return  orderService.verifyPayment(request);
    }
}
