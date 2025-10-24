package com.hcltech.shopease.controller;

import com.hcltech.shopease.model.Purchase;
import com.hcltech.shopease.model.User;
import com.hcltech.shopease.repository.PurchaseRepository;
import com.hcltech.shopease.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hcltech.shopease.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List; import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController @RequestMapping("/api/user") public class UserController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(Authentication authentication){
        // authentication.getName() is username because JwtAuthenticationFilter set it as principal
        String username = (String) authentication.getPrincipal();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        long purchasesCount = purchaseRepository.findByUser(user).size();
        return ResponseEntity.ok(Map.of("username", user.getUsername(), "email", user.getEmail(), "purchasesCount", purchasesCount));
    }

    @GetMapping("/purchases")
    public ResponseEntity<?> purchases(Authentication authentication){
        String username = (String) authentication.getPrincipal();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Purchase> purchases = purchaseRepository.findByUser(user);
        var dto = purchases.stream().map(p -> Map.of(
                "productName", p.getProduct().getName(),
                "purchaseDate", p.getPurchaseDate(),
                "quantity", p.getQuantity(),
                "price", p.getProduct().getPrice(),
                "totalAmount", p.getTotalAmount()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

}

