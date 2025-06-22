package com.pm.login.controller;

import com.pm.login.dto.LoginDTO;
import com.pm.login.dto.LoginResponseDTO;
import com.pm.login.dto.RequestDTO;
import com.pm.login.dto.ResponseDTO;
import com.pm.login.jwt.JwtUtil;
import com.pm.login.repository.UsuarioRepository;
import com.pm.login.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operaciones relacionadas con el registro y acceso de un usuario")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @Operation(summary = "Registro de un usuario", description = "Registra un usuario nuevo")
    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> registrarUsuario(@RequestBody @Valid RequestDTO requestDTO) {
        ResponseDTO responseDTO = usuarioService.create(requestDTO);
        String token = jwtUtil.generateToken(responseDTO.getEmail());
        return ResponseEntity.ok(
                LoginResponseDTO.builder()
                        .token(token)
                        .user(responseDTO)
                        .build()
        );
    }

    @Operation(summary = "Inicio de sesión", description = "Inicia sesión del usuario")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            String token = jwtUtil.generateToken(loginDTO.getEmail());

            ResponseDTO user = usuarioRepository.findByEmail(loginDTO.getEmail())
                    .map(u -> ResponseDTO.builder()
                            .nombre(u.getNombre())
                            .email(u.getEmail())
                            .build())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            return ResponseEntity.ok(
                    LoginResponseDTO.builder()
                            .token(token)
                            .user(user)
                            .build()
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
