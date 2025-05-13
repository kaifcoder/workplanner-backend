package com.example.servicemarketplace.controller;


import com.example.servicemarketplace.Dto.LoginRequest;
import com.example.servicemarketplace.Dto.Response;
import com.example.servicemarketplace.model.Users;
import com.example.servicemarketplace.repository.UserRepository;
import com.example.servicemarketplace.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        user.setRole("ROLE_USER");
        return ResponseEntity.ok(userRepo.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Users user = userRepo.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Found with username: " + loginRequest.getUsername()));
        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid Password");
        }
        Response response = Response.builder()
                .message("Login successful")
                .status("success")
                .token(jwtUtils.generateToken(user))
                .build();
        return ResponseEntity.ok(response);

    }

}
