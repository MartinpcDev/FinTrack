package com.fintrack.api.service;

import com.fintrack.api.persistence.dto.request.LoginRequest;
import com.fintrack.api.persistence.dto.request.RegisterRequest;
import com.fintrack.api.persistence.dto.response.LoginResponse;
import com.fintrack.api.persistence.dto.response.RegisterResponse;

public interface AuthService {

  RegisterResponse register(RegisterRequest request);

  LoginResponse login(LoginRequest request);
}
