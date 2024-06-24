package com.example.SeguimientoHallazgo.Auth;

import com.example.SeguimientoHallazgo.Common.Role;
import com.example.SeguimientoHallazgo.Domain.User;
import com.example.SeguimientoHallazgo.Jwt.JwtService;
import com.example.SeguimientoHallazgo.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest request) throws JsonProcessingException {
        var authResult = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        boolean isAdmin = roles.stream().anyMatch(r -> r.getAuthority().equals("ADMIN"));

        Long userId = user.getId();

        Claims claims = Jwts.claims();
        claims.put("isAdmin", isAdmin);
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
        claims.put("id", userId);

        var jwtToken = jwtService.getToken(claims, user);

        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
