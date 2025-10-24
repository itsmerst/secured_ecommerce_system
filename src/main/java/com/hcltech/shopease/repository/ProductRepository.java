package com.hcltech.shopease.repository;

import com.hcltech.shopease.model.Product; import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}

