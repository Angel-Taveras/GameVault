package com.pm.login.dto;



import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ResponseDTO {

    private UUID id;
    private String nombre;
    private String email;

}
