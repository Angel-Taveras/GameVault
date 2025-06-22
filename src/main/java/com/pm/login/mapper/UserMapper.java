package com.pm.login.mapper;

import com.pm.login.dto.RequestDTO;
import com.pm.login.dto.ResponseDTO;
import com.pm.login.model.Usuario;

public class UserMapper {

    public static Usuario toEntity(RequestDTO dto){
        if (dto == null){
            return null;
        }

        return Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

    }

    public static ResponseDTO toResponseDTO(Usuario usuario){
        if (usuario == null){
            return null;
        }

        return ResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .build();

    }
}
