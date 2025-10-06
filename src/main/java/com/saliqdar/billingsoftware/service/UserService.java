package com.saliqdar.billingsoftware.service;

import com.saliqdar.billingsoftware.io.UserRequest;
import com.saliqdar.billingsoftware.io.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    String getUserRole(String email);
    List<UserResponse> readUsers();
    void deleteUser(String id);
}
