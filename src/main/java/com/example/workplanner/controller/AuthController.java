package com.example.workplanner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.workplanner.Dto.LoginRequest;
import com.example.workplanner.Dto.Response;
import com.example.workplanner.model.Users;
import com.example.workplanner.repository.UserRepository;
import com.example.workplanner.security.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

  ;  @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        // Default to TEAM_MEMBER if not provided, else allow MANAGER if explicitly set

        if (user.getRole() == null || (!user.getRole().equalsIgnoreCase("MANAGER") && !user.getRole().equalsIgnoreCase("TEAM_MEMBER"))) {
            user.setRole("ROLE_"+"TEAM_MEMBER");
        } else {
            user.setRole("ROLE_"+user.getRole().toUpperCase());
        }
        return ResponseEntity.ok(userRepo.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Users user = userRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Found with username: " + loginRequest.getUsername()));
        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid Password");
        }
        String jwt = jwtUtils.generateToken(user);
        Response response = Response.builder()
                .message("Login successful")
                .status("success")
                .token(jwt)
                .build();
        ResponseCookie springCookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(response);
    }

}
