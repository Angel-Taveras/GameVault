package com.pm.login.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {

    private UUID id;
    private String nombre;
    private String email;


    public ResponseDTO(String nombre, @Email @NotBlank(message = "El campo email es obligatorio") String email) {
    }
}
