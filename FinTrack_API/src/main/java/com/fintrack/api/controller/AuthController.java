package com.fintrack.api.controller;

import com.fintrack.api.persistence.dto.request.LoginRequest;
import com.fintrack.api.persistence.dto.request.RefreshRequest;
import com.fintrack.api.persistence.dto.request.RegisterRequest;
import com.fintrack.api.persistence.dto.response.LoginResponse;
import com.fintrack.api.persistence.dto.response.ProfileResponse;
import com.fintrack.api.persistence.dto.response.RegisterResponse;
import com.fintrack.api.persistence.model.User;
import com.fintrack.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping("/refresh-token")
  public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(authService.refreshToken(request));
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/profile")
  public ResponseEntity<ProfileResponse> profile(@AuthenticationPrincipal UserDetails userDetails) {
    Long userId = extractUserId(userDetails);
    return ResponseEntity.status(HttpStatus.OK)
        .body(authService.getProfile(userId));
  }

  private Long extractUserId(UserDetails userDetails) {
    return ((User) userDetails).getId();
  }
}
