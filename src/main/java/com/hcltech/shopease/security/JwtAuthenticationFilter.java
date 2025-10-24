package com.hcltech.shopease.security;

import com.auth0.jwt.interfaces.DecodedJWT; import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException; import java.util.List;

@Component public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")){
            try{
                String token = header.substring(7);
                DecodedJWT decoded = jwtUtil.validateTokenAndGet(token);
                String username = decoded.getSubject();
                String role = decoded.getClaim("role").asString();
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex){
                // invalid token -> clear context; let security handle forbidden
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

}