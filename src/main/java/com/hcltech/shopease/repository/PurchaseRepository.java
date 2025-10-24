package com.hcltech.shopease.repository;

import com.hcltech.shopease.model.Purchase;
import com.hcltech.shopease.model.User;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> { List<Purchase> findByUser(User user); }
