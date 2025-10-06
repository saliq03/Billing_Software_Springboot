package com.saliqdar.billingsoftware.io;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String email;
    private String name;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
