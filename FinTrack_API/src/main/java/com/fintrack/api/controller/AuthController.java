package com.fintrack.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

  @PostMapping("/register")
  public ResponseEntity<?> register(){
    return ResponseEntity.ok("Register");
  }
}
