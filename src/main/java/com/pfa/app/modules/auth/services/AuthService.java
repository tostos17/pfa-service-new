package com.pfa.app.modules.auth.services;

import com.pfa.app.modules.auth.dto.AuthResponse;
import com.pfa.app.modules.auth.dto.LoginRequest;
import com.pfa.app.modules.auth.dto.RegisterRequest;
import com.pfa.app.modules.user.entities.Role;
import com.pfa.app.modules.user.entities.User;
import com.pfa.app.modules.user.repositories.RoleRepository;
import com.pfa.app.modules.user.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret:fallbackSecretKeyThatIsAtLeast32BytesLongForSecurity}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;

    public AuthResponse authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        // Match raw password against encrypted DB password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = generateToken(user);

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().getName(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    @Transactional
    public User registerNewUser(RegisterRequest request) {
        // 1. Guard check: Prevent duplicate email registration accounts
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("An account with this email address already exists.");
        }

        // 2. Resolve Role Entity context from database lookup matching requested ID
        Role assignedRole = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("The specified system authorization profile role was not found."));

        // 3. Build state profile and encrypt the raw string text password securely
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setRole(assignedRole);

        // Hashing passwords via BCrypt prevents security risks from plaintext storage leaks
        String secureHash = passwordEncoder.encode(request.getPassword_hash());
        newUser.setPasswordHash(secureHash);

        // 4. Record output confirmation save trace entry down into PostgreSQL
        return userRepository.save(newUser);
    }

    private String generateToken(User user) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().getName())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
