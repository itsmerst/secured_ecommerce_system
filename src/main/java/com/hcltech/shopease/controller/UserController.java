package com.hcltech.shopease.controller;

import com.hcltech.shopease.model.Purchase;
import com.hcltech.shopease.model.User;
import java.util.Map;
import com.hcltech.shopease.repository.PurchaseRepository;
import com.hcltech.shopease.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hcltech.shopease.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user") public class UserController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
public ResponseEntity<?> getDashboard(Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username).orElseThrow();
    return ResponseEntity.ok(Map.of(
        "message", "Welcome " + user.getUsername(),
        "email", user.getEmail()
    ));
}

@GetMapping("/purchases")
public ResponseEntity<?> getUserPurchases(Authentication authentication) {
    String username = authentication.getName();
    User user = userRepository.findByUsername(username).orElseThrow();
    List<Purchase> purchases = purchaseRepository.findByUserId(user.getId());
    return ResponseEntity.ok(purchases);
}


}

