package com.pm.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class RequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @Email(message = "Correo no válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

}
