package com.saliqdar.billingsoftware.controller;

import com.razorpay.Payment;
import com.razorpay.RazorpayException;
import com.saliqdar.billingsoftware.io.PaymentRequest;
import com.saliqdar.billingsoftware.io.RazorpayOrderResponse;
import com.saliqdar.billingsoftware.service.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private  final RazorpayService razorpayService;

    @PostMapping
    public RazorpayOrderResponse createPayment(@RequestBody PaymentRequest paymentRequest) throws RazorpayException {
        return razorpayService.createOrder(paymentRequest);
    }
}
