package com.saliqdar.billingsoftware.io;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String itemId;
    private String name;
    private BigDecimal price;
    private String description;
    private String imgUrl;
    private String categoryName;
    private String categoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
