package com.pm.login.dto;


import com.pm.login.Enum.OwnershipStatus;
import com.pm.login.Enum.Platform;
import com.pm.login.Enum.PlayStatus;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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
