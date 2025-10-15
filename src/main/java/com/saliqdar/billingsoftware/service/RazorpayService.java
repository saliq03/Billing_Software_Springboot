package com.saliqdar.billingsoftware.service;

import com.razorpay.RazorpayException;
import com.saliqdar.billingsoftware.io.RazorpayOrderResponse;

public interface RazorpayService {
    RazorpayOrderResponse createOrder(Double amount,String currency) throws RazorpayException;
}
