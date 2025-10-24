package com.hcltech.shopease.controller;

import com.hcltech.shopease.model.User; import com.hcltech.shopease.repository.PurchaseRepository; import com.hcltech.shopease.repository.UserRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/admin") public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping("/users")
public List<User> getAllUsers() {
    return userRepository.findAll();
}

@GetMapping("/purchases")
public List<Purchase> getAllPurchases() {
    return purchaseRepository.findAll();
}


}
