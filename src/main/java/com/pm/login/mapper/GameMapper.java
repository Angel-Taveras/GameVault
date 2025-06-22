package com.pm.login.mapper;

import com.pm.login.dto.RequestGameDTO;
import com.pm.login.dto.ResponseGameDTO;
import com.pm.login.model.Game;

public class GameMapper {



    public static Game toEntity(RequestGameDTO dto){
        if(dto == null) return null;

        return Game.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .platform(dto.getPlatform())
                .releaseYear(dto.getReleaseYear())
                .status(dto.getStatus())
                .playStatus(dto.getPlayStatus())
                .coverImageUrl(dto.getCoverImageUrl())
                .build();
    }

    public static ResponseGameDTO toResponseDTO(Game game){
            if (game == null) return null;

            return ResponseGameDTO.builder()
                    .id(game.getId())
                    .name(game.getName())
                    .description(game.getDescription())
                    .platform(game.getPlatform())
                    .releaseYear(game.getReleaseYear())
                    .status(game.getStatus())
                    .playStatus(game.getPlayStatus())
                    .coverImageUrl(game.getCoverImageUrl())
                    .build();
    }
}
