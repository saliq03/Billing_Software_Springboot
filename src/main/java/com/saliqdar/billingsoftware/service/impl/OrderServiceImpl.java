package com.saliqdar.billingsoftware.service.impl;

import com.saliqdar.billingsoftware.entity.OrderEntity;
import com.saliqdar.billingsoftware.entity.OrderItemEntity;
import com.saliqdar.billingsoftware.exception.NotFoundException;
import com.saliqdar.billingsoftware.io.OrderRequest;
import com.saliqdar.billingsoftware.io.OrderResponse;
import com.saliqdar.billingsoftware.io.PaymentDetails;
import com.saliqdar.billingsoftware.io.PaymentMethod;
import com.saliqdar.billingsoftware.repository.OrderRepository;
import com.saliqdar.billingsoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        OrderEntity orderEntity = convertToOrderEntity(orderRequest);
       orderEntity= orderRepository.save(orderEntity);
        return convertToOrderResponse(orderEntity);
    }

    private OrderResponse convertToOrderResponse(OrderEntity orderEntity) {
        List<OrderResponse.OrderItemResponse> orderItemsResponse=orderEntity
                .getCartItems()
                .stream()
                .map(this::convertToOrderItemResponse)
                .collect(Collectors.toList());
        return OrderResponse.builder()
                .orderId(orderEntity.getOrderId())
                .customerName(orderEntity.getCustomerName())
                .phoneNumber(orderEntity.getPhoneNumber())
                .subtotal(orderEntity.getSubtotal())
                .tax(orderEntity.getTax())
                .grandTotal(orderEntity.getGrandTotal())
                .cartItems(orderItemsResponse)
                .paymentMethod(orderEntity.getPaymentMethod())
                .paymentDetails(orderEntity.getPaymentDetails())
                .createdAt(orderEntity.getCreatedAt())
                .build();
    }

    private OrderResponse.OrderItemResponse convertToOrderItemResponse(OrderItemEntity orderItemEntity) {
        return OrderResponse.OrderItemResponse.builder()
                .itemId(orderItemEntity.getItemId())
                .quantity(orderItemEntity.getQuantity())
                .price(orderItemEntity.getPrice())
                .name(orderItemEntity.getName())
                .build();
    }

    private OrderEntity convertToOrderEntity(OrderRequest orderRequest) {


        List<OrderItemEntity> orderItemEntities=orderRequest
                .getCartItems()
                .stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());
        OrderEntity orderEntity= OrderEntity.builder()
                .customerName(orderRequest.getCustomerName())
                .phoneNumber(orderRequest.getPhoneNumber())
                .subtotal(orderRequest.getSubtotal())
                .tax(orderRequest.getTax())
                .grandTotal(orderRequest.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(orderRequest.getPaymentMethod()))
                .cartItems(orderItemEntities)
                .build();

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStatus(orderEntity.getPaymentMethod()==PaymentMethod.CASH?
                PaymentDetails.PaymentStatus.COMPLETED: PaymentDetails.PaymentStatus.PENDING);

        orderEntity.setPaymentDetails(paymentDetails);
        return  orderEntity;
    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemEntity) {
        return OrderItemEntity.builder()
                .itemId(orderItemEntity.getItemId())
                .quantity(orderItemEntity.getQuantity())
                .price(orderItemEntity.getPrice())
                .name(orderItemEntity.getName())
                .build();
    }

    @Override
    public void deleteOrder(String orderId) {
        OrderEntity orderEntity=orderRepository.findByOrderId(orderId)
                .orElseThrow(()->new NotFoundException("Order not found with id: " + orderId));
        orderRepository.delete(orderEntity);
    }

    @Override
    public List<OrderResponse> getLatestOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToOrderResponse)
                .collect(Collectors.toList());
    }
}
