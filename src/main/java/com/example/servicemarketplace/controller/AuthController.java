package com.example.servicemarketplace.controller;

import com.example.servicemarketplace.model.Userentity;
import com.example.servicemarketplace.repository.UserRepository;
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
    @Autowired private PasswordEncoder encoder; //

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Userentity userentity) {
        userentity.setPassword(encoder.encode(userentity.getPassword()));
        userentity.setRole("ROLE_USER");
        return ResponseEntity.ok(userRepo.save(userentity));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        // Spring Basic Auth handles login, this just confirms it
        return ResponseEntity.ok("Login Successful");
    }

}
