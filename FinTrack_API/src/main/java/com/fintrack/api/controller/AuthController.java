package com.fintrack.api.controller;

import com.fintrack.api.persistence.dto.request.LoginRequest;
import com.fintrack.api.persistence.dto.request.RegisterRequest;
import com.fintrack.api.persistence.dto.response.LoginResponse;
import com.fintrack.api.persistence.dto.response.RegisterResponse;
import com.fintrack.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(authService.login(request));
  }
}
