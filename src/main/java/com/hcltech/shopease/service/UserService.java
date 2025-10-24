package com.hcltech.shopease.service;

import com.hcltech.shopease.model.Role; import com.hcltech.shopease.model.User; import com.hcltech.shopease.repository.UserRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service;

import java.util.List;

@Service public class UserService { @Autowired private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String rawPassword){
        if(userRepository.existsByUsername(username)) throw new RuntimeException("Username already exists");
        if(userRepository.existsByEmail(email)) throw new RuntimeException("Email already exists");
        User u = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .role(Role.ROLE_USER)
                .build();
        return userRepository.save(u);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}

