package com.pm.login.service;

import com.pm.login.dto.LoginDTO;
import com.pm.login.dto.RequestDTO;
import com.pm.login.dto.ResponseDTO;
import com.pm.login.exception.AuthException;
import com.pm.login.mapper.UserMapper;
import com.pm.login.model.Usuario;
import com.pm.login.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {



    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseDTO create(RequestDTO dto) {

        String hashedPassword = passwordEncoder.encode(dto.getPassword());


        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(hashedPassword)
                .build();


        Usuario guardado = usuarioRepository.save(usuario);


        return UserMapper.toResponseDTO(guardado);
    }

    public ResponseDTO login(LoginDTO loginDTO) {

       Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new AuthException("Usuario no encontrado con ese correo"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
            throw new AuthException("Contraseña incorrecta");
        }

        return UserMapper.toResponseDTO(usuario);
    }
}

