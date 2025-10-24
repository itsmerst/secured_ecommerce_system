package com.hcltech.shopease.controller;

import com.hcltech.shopease.model.User; import com.hcltech.shopease.security.JwtUtil; import com.hcltech.shopease.service.UserService; import com.hcltech.shopease.repository.UserRepository; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.http.ResponseEntity; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.validation.annotation.Validated; import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank; import java.util.Map;

@RestController @RequestMapping("/api/auth") @Validated public class AuthController { @Autowired private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    record RegisterRequest(@NotBlank String username, @NotBlank String email, @NotBlank String password){}
    record LoginRequest(@NotBlank String username, @NotBlank String password){}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req){
        User u = userService.registerUser(req.username(), req.email(), req.password());
        return ResponseEntity.ok(Map.of("message","User registered", "username", u.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req){
        var opt = userRepository.findByUsername(req.username());
        if(opt.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","Invalid credentials"));
        User u = opt.get();
        if(!passwordEncoder.matches(req.password(), u.getPassword())){
            return ResponseEntity.status(401).body(Map.of("error","Invalid credentials"));
        }
        String token = jwtUtil.generateToken(u.getUsername(), u.getRole().name());
        return ResponseEntity.ok(Map.of("token", token));
    }

}
