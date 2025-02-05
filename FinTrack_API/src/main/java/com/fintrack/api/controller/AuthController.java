package com.fintrack.api.controller;

import com.fintrack.api.persistence.dto.request.LoginRequest;
import com.fintrack.api.persistence.dto.request.RefreshRequest;
import com.fintrack.api.persistence.dto.request.RegisterRequest;
import com.fintrack.api.persistence.dto.response.LoginResponse;
import com.fintrack.api.persistence.dto.response.ProfileResponse;
import com.fintrack.api.persistence.dto.response.RegisterResponse;
import com.fintrack.api.persistence.model.User;
import com.fintrack.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "Endpoints para autenticación")
public class AuthController {

  private final AuthService authService;

  @Operation(
      summary = "Registro de usuario",
      description = "Registro de un nuevo usuario en la aplicación",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Datos del usuario a registrar",
          required = true,
          content = @Content(mediaType = "application/json", schema = @Schema(implementation =
              RegisterRequest.class))
      ),
      responses = {
          @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                  RegisterResponse.class))),
          @ApiResponse(responseCode = "400", description = "Datos de registro inválidos",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
              content = @Content(mediaType = "application/json"))
      }
  )
  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(authService.register(request));
  }

  @Operation(
      summary = "Login User",
      description = "Autentica a un usuario y devuelve un token JWT.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Credenciales del usuario (username y contraseña).",
          required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = LoginRequest.class))
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = LoginResponse.class))),
          @ApiResponse(responseCode = "401", description = "Credenciales incorrectas",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
              content = @Content(mediaType = "application/json"))
      }
  )
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(authService.login(request));
  }

  @Operation(
      summary = "Refresh Token",
      description = "Refresh the authentication token using a valid refresh token.",
      tags = {"Auth"},
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Refresh token request object",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = RefreshRequest.class)
          )
      ),
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "New access token generated",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LoginResponse.class)
              )
          ),
          @ApiResponse(
              responseCode = "403",
              description = "Invalid or expired refresh token",
              content = @Content(mediaType = "application/json")
          )
      }
  )
  @PostMapping("/refresh-token")
  public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(authService.refreshToken(request));
  }

  @Operation(
      summary = "Get User Profile",
      description = "Retrieve the profile information of the authenticated user.",
      security = @SecurityRequirement(name = "Security Token"),
      tags = {"Auth"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "User profile retrieved successfully",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ProfileResponse.class)
              )
          ),
          @ApiResponse(
              responseCode = "401",
              description = "Unauthorized - Invalid or missing authentication token",
              content = @Content(mediaType = "application/json")
          )
      }
  )
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
