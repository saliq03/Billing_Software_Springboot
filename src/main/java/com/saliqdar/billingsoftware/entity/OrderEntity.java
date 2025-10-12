package com.saliqdar.billingsoftware.entity;

import com.saliqdar.billingsoftware.io.PaymentDetails;
import com.saliqdar.billingsoftware.io.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(unique = true)
    private  String orderId;
    private  String customerName;
    private  String phoneNumber;
    private Double subtotal;
    private Double tax;
    private Double grandTotal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // cascade type.all means andy operation to order table will also apply to order items table  and orphan removal=true means if any item removed from list in java should also remove from data base
    @JoinColumn(name = "order_id")
    private List<OrderItemEntity> cartItems=new ArrayList<>();


    @Embedded   // this will not create separate table, will store in same table
    private PaymentDetails paymentDetails;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private LocalDateTime createdAt;

    @PrePersist
    protected  void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.orderId ="ORD"+ System.currentTimeMillis();
    }

}
