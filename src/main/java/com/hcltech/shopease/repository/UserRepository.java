package com.hcltech.shopease.repository;

import com.hcltech.shopease.model.User;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { Optional<User> findByUsername(String username); 
                                                                   boolean existsByUsername(String username); 
                                                                   boolean existsByEmail(String email); 
                                                                   List<Purchase> findByUserId(Long userId);

                                                                  }
