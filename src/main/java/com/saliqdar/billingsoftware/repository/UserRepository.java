package com.saliqdar.billingsoftware.repository;

import com.saliqdar.billingsoftware.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional <UserEntity> findByEmail(String email);
    Optional <UserEntity> findByUserId(String userId);
}
