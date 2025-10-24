package com.hcltech.shopease.security;

import jakarta.servlet.ServletException; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse; import org.springframework.security.access.AccessDeniedException; import org.springframework.security.web.access.AccessDeniedHandler; import org.springframework.stereotype.Component;

import java.io.IOException;

@Component public class CustomAccessDeniedHandler implements AccessDeniedHandler { @Override public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    // For a web UI, redirect to a friendly page
    response.sendRedirect("/access-denied"); } }