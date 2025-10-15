package com.saliqdar.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RazorpayOrderResponse {
    private String id;
    private String entity;
    private Integer amount;
    private String currency;
    private String status;
    private String receipt;
    private Date createdAt;
}
