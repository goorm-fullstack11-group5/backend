package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.config.JwtConfig;
import goorm.fullstack.webide.domain.User;
import goorm.fullstack.webide.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtConfig jwtConfig;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtConfig jwtConfig, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtConfig = jwtConfig;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");

        User user = userService.registerUser(username, password, email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        return userService.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    String token = Jwts.builder()
                            .setSubject(user.getUsername())
                            .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationTime()))
                            .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey())
                            .compact();

                    Map<String, String> response = new HashMap<>();
                    response.put("token", token);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "아이디 및 비밀번호 오류");
                    return ResponseEntity.status(401).body(errorResponse);
                });
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("JWT 토큰이 필요합니다.");
        }

        String token = authorizationHeader.substring(7); // "Bearer " 제거
        String username = Jwts.parser()
                .setSigningKey(jwtConfig.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        Map<String, String> response = new HashMap<>();
        response.put("message", username + "님이 로그아웃되었습니다.");
        return ResponseEntity.ok(response);
    }
}
