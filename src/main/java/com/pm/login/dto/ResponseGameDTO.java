package com.pm.login.dto;


import com.pm.login.Enum.OwnershipStatus;
import com.pm.login.Enum.Platform;
import com.pm.login.Enum.PlayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseGameDTO {

    private UUID id;

    private String name;

    private String description;

    private Platform platform;

    private int releaseYear;

    private OwnershipStatus status;

    private PlayStatus playStatus;

    private String coverImageUrl;
}
