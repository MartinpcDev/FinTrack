package com.fintrack.api.service.Impl;

import com.fintrack.api.exception.InvalidAuthException;
import com.fintrack.api.exception.UserNotFoundException;
import com.fintrack.api.persistence.dto.request.LoginRequest;
import com.fintrack.api.persistence.dto.request.RefreshRequest;
import com.fintrack.api.persistence.dto.request.RegisterRequest;
import com.fintrack.api.persistence.dto.response.LoginResponse;
import com.fintrack.api.persistence.dto.response.ProfileResponse;
import com.fintrack.api.persistence.dto.response.RegisterResponse;
import com.fintrack.api.persistence.model.User;
import com.fintrack.api.persistence.repository.UserRepository;
import com.fintrack.api.service.AuthService;
import com.fintrack.api.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtUtils jwtUtils;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  public RegisterResponse register(RegisterRequest request) {
    if (userRepository.existsByUsernameIgnoreCase(request.username())
        || userRepository.existsByEmailIgnoreCase(request.password())) {
      throw new InvalidAuthException("El email o el username de usuario ya están en uso");
    }

    User user = new User();
    user.setName(request.name());
    user.setEmail(request.email());
    user.setUsername(request.username());
    user.setPassword(passwordEncoder.encode(request.password()));
    userRepository.save(user);

    return new RegisterResponse("Usuario registrado con éxito");
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    User userExists = userRepository.findByUsernameIgnoreCase(request.username())
        .orElseThrow(() -> new InvalidAuthException("Usuario no encontrado"));

    if (!passwordEncoder.matches(request.password(), userExists.getPassword())) {
      throw new InvalidAuthException("Contraseña incorrecta");
    }

    String accessToken = jwtUtils.generateAccessToken(userExists);
    String refreshToken = jwtUtils.generateRefreshToken(userExists);

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.username(), request.password())
    );

    return new LoginResponse(accessToken, refreshToken, "Inicio de sesión exitoso");
  }

  @Override
  public ProfileResponse getProfile(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
    return new ProfileResponse(user.getId(), user.getName(), user.getUsername(), user.getEmail());
  }

  @Override
  public LoginResponse refreshToken(RefreshRequest request) {
    String refreshToken = request.refreshToken();
    String username = jwtUtils.extractUsername(refreshToken);
    User user = userRepository.findByUsernameIgnoreCase(username)
        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

    if (!jwtUtils.isTokenValid(refreshToken, user)) {
      throw new InvalidAuthException("Token inválido");
    }

    String newAccessToken = jwtUtils.generateAccessToken(user);
    return new LoginResponse(newAccessToken, refreshToken, "Token actualizado");
  }
}
