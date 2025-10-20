package com.saliqdar.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVerificationRequest {
    private String orderId;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
