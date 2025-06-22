package com.pm.login.model;

import com.pm.login.Enum.OwnershipStatus;
import com.pm.login.Enum.Platform;
import com.pm.login.Enum.PlayStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "games")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    private String name;


    private String description;


    @Enumerated(EnumType.STRING)
    private Platform platform;


    private int releaseYear;


    @Enumerated(EnumType.STRING)
    private OwnershipStatus status;


    @Enumerated(EnumType.STRING)
    @Column(name = "play_status")
    private PlayStatus playStatus;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String coverImageUrl;

}
