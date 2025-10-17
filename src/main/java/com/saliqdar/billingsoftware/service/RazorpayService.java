package com.saliqdar.billingsoftware.service;

import com.razorpay.RazorpayException;
import com.saliqdar.billingsoftware.io.PaymentRequest;
import com.saliqdar.billingsoftware.io.RazorpayOrderResponse;

public interface RazorpayService {
    RazorpayOrderResponse createOrder(PaymentRequest paymentRequest) throws RazorpayException;
}
