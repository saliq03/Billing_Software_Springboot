package com.saliqdar.billingsoftware.service.impl;

import com.saliqdar.billingsoftware.entity.UserEntity;
import com.saliqdar.billingsoftware.exception.NotFoundException;
import com.saliqdar.billingsoftware.io.UserRequest;
import com.saliqdar.billingsoftware.io.UserResponse;
import com.saliqdar.billingsoftware.repository.UserRepository;
import com.saliqdar.billingsoftware.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder(12);
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        UserEntity userEntity=convertToEntity(userRequest);
        userRepository.save(userEntity);
        return convertToResponse(userEntity);
    }

    private UserResponse convertToResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    private UserEntity convertToEntity(UserRequest userRequest) {
        return  UserEntity
                .builder()
                .userId(UUID.randomUUID().toString())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .name(userRequest.getName())
                .role(userRequest.getRole())
                .build();
    }

    @Override
    public String getUserRole(String email) {
        UserEntity userEntity=userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("User not found with email: " + email));
        return userEntity.getRole();
    }

    @Override
    public List<UserResponse> readUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {
     UserEntity userEntity=userRepository.findByUserId(id)
             .orElseThrow(()-> new NotFoundException("User not found for id "+id));
     userRepository.delete(userEntity);
    }
}
