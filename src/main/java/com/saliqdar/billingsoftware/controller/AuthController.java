package com.saliqdar.billingsoftware.controller;

import com.saliqdar.billingsoftware.io.AuthRequest;
import com.saliqdar.billingsoftware.io.AuthResponse;
import com.saliqdar.billingsoftware.io.CategoryResponse;
import com.saliqdar.billingsoftware.repository.UserRepository;
import com.saliqdar.billingsoftware.service.UserService;
import com.saliqdar.billingsoftware.service.impl.AppUserDetailsService;
import com.saliqdar.billingsoftware.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserDetailsService appUserDetailsService;
    private final UserService userService;

    @PostMapping("/encode")
    public String encodePassword(@RequestBody Map<String,String> response) {
        return passwordEncoder.encode(response.get("password"));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        authenticate(authRequest.getEmail(),authRequest.getPassword());
        UserDetails userDetails=appUserDetailsService.loadUserByUsername(authRequest.getEmail());
       String token=jwtUtil.generateToken(userDetails);
       String role= userService.getUserRole(authRequest.getEmail());
        return new AuthResponse(authRequest.getEmail(),token,role);
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }
        catch (DisabledException e) {
          throw new Exception("User Disabled");
        }
        catch (BadCredentialsException e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect email or password");
        }
    }

}
