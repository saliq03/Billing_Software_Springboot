package com.saliqdar.billingsoftware.controller;

import com.saliqdar.billingsoftware.io.UserRequest;
import com.saliqdar.billingsoftware.io.UserResponse;
import com.saliqdar.billingsoftware.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest userRequest){
        try{
            return userService.createUser(userRequest);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unable to create user "+ e.getMessage());
        }

    }

    @GetMapping("/users")
    public List<UserResponse> readUsers() {
        return userService.readUsers();
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id){

        try{
            userService.deleteUser(id);
        }
        catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found\n"+ e.getMessage());
        }
    }



}
