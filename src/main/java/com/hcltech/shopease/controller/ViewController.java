package com.hcltech.shopease.controller;

import org.springframework.stereotype.Controller; import org.springframework.web.bind.annotation.GetMapping;

@Controller public class ViewController {
    // simple access-denied page for web UI redirect
    @GetMapping("/access-denied") public String accessDenied(){
        return "access-denied";
        // map to src/main/resources/templates/access-denied.html if using Thymeleaf
        }
}
