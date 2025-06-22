package com.pm.login.dto;


import com.pm.login.Enum.OwnershipStatus;
import com.pm.login.Enum.Platform;
import com.pm.login.Enum.PlayStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestGameDTO {

    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotNull(message = "La plataforma es obligatoria")
    private Platform platform;

    @NotNull(message = "El año de lanzamiento es obligatorio")
    private int releaseYear;

    @NotNull(message = "El estado de propiedad es obligatorio")
    private OwnershipStatus status;

    @NotNull(message = "El estado de juego es obligatorio")
    private PlayStatus playStatus;

    private String coverImageUrl;



}
