package com.saliqdar.billingsoftware.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

        // 256-bit base64 encoded key (example)
        @Value("${jwt.secret_key}")
         private String secretKey;


        public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims=new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24*7)) //7 days
                .and()
                .signWith(getKeY())
                .compact();
        }

        private SecretKey getKeY() {
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

        public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

        public boolean validateToken(String token, UserDetails userDetails) {
        String username=extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
        private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

        private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKeY())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    }
