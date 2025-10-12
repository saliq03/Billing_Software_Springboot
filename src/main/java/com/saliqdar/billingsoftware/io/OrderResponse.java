package com.saliqdar.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String orderId;
    private  String customerName;
    private  String phoneNumber;
    private Double subtotal;
    private Double tax;
    private Double grandTotal;
    private List<OrderItemResponse> cartItems;
    private PaymentMethod paymentMethod;
    private PaymentDetails paymentDetails;
    private LocalDateTime createdAt;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public  static class OrderItemResponse{
        private String itemId;
        private String name;
        private Double price;
        private Integer quantity;
    }
}
