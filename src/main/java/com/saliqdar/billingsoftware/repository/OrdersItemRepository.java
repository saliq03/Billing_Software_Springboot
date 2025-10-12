package com.saliqdar.billingsoftware.repository;

import com.saliqdar.billingsoftware.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersItemRepository extends JpaRepository<OrderItemEntity,Long> {
}
