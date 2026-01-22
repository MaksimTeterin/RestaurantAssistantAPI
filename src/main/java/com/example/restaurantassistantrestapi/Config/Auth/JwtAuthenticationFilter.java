package com.example.restaurantassistantrestapi.Config.Auth;

import com.example.restaurantassistantrestapi.model.User;
import com.example.restaurantassistantrestapi.service.UserService;
import com.example.restaurantassistantrestapi.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter called");

        String token = getTokenFromRequest(request);
        System.out.println("token: " + token);
        if(token != null && jwtService.validateToken(token) != null) {

            User user = userService.getUserByEmail(jwtService.validateToken(token));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    List.of(new SimpleGrantedAuthority(user.getUserRoles().toString()))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            System.out.println("clientExistsByEmail: " + jwtService.validateToken(token));
        }

        filterChain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        System.out.println("getTokenFromRequest: " + request.getRequestURI());
        String header = request.getHeader("Authorization");

        return (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
    }
}
