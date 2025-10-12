package com.saliqdar.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private  String customerName;
    private  String phoneNumber;
    private Double subtotal;
    private Double tax;
    private Double grandTotal;
    private List<OrderItemRequest> cartItems;
    private String paymentMethod;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public  static class OrderItemRequest{
        private String itemId;
        private String name;
        private Double price;
        private Integer quantity;
    }
}
