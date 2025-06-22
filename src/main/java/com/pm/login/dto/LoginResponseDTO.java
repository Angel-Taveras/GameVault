package com.pm.login.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LoginResponseDTO {

    String token;
    ResponseDTO user;
}
