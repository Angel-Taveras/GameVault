package com.pm.login.service;

import com.pm.login.Enum.OwnershipStatus;
import com.pm.login.Enum.PlayStatus;
import com.pm.login.dto.RequestGameDTO;
import com.pm.login.dto.ResponseGameDTO;
import com.pm.login.exception.NotFoundException;
import com.pm.login.mapper.GameMapper;
import com.pm.login.model.Game;
import com.pm.login.model.Usuario;
import com.pm.login.repository.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public ResponseGameDTO saveGame(RequestGameDTO requestGameDTO) {
        Usuario usuario = obtenerUsuarioAutenticado();

        Game game = GameMapper.toEntity(requestGameDTO);
        game.setUsuario(usuario);

        Game saved = gameRepository.save(game);
        return GameMapper.toResponseDTO(saved);
    }

    public List<ResponseGameDTO> getAllGame() {
        Usuario user = obtenerUsuarioAutenticado();

        return gameRepository.findByUsuarioEmail(user.getEmail())
                .stream()
                .map(GameMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ResponseGameDTO getGameById(UUID id) {
        Usuario user = obtenerUsuarioAutenticado();

        Game game = gameRepository.findById(id)
                .filter(g -> g.getUsuario().getId().equals(user.getId()))
                .orElseThrow(() -> new NotFoundException("Game not found or access denied"));

        return GameMapper.toResponseDTO(game);
    }

    public void deleteGameById(UUID id) {
        Usuario user = obtenerUsuarioAutenticado();

        Game game = gameRepository.findById(id)
                .filter(g -> g.getUsuario().getId().equals(user.getId()))
                .orElseThrow(() -> new NotFoundException("Game not found or access denied"));

        gameRepository.delete(game);
    }

    public ResponseGameDTO updateGameById(UUID id, RequestGameDTO dto) {
        Usuario user = obtenerUsuarioAutenticado();

        Game game = gameRepository.findById(id)
                .filter(g -> g.getUsuario().getId().equals(user.getId()))
                .orElseThrow(() -> new NotFoundException("Game not found or access denied"));

        game.setName(dto.getName());
        game.setDescription(dto.getDescription());
        game.setPlatform(dto.getPlatform());
        game.setStatus(dto.getStatus());
        game.setPlayStatus(dto.getPlayStatus());
        game.setReleaseYear(dto.getReleaseYear());
        game.setCoverImageUrl(dto.getCoverImageUrl());

        Game updated = gameRepository.save(game);
        return GameMapper.toResponseDTO(updated);
    }

    public ResponseGameDTO updateGameStatus(UUID id, Map<String, String> updates) {
        Usuario user = obtenerUsuarioAutenticado();

        Game game = gameRepository.findById(id)
                .filter(g -> g.getUsuario().getId().equals(user.getId()))
                .orElseThrow(() -> new NotFoundException("Game not found or access denied"));

        if (updates.containsKey("playStatus")) {
            try {
                game.setPlayStatus(PlayStatus.valueOf(updates.get("playStatus")));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado de juego no válido");
            }
        }

        if (updates.containsKey("ownershipStatus")) {
            try {
                game.setStatus(OwnershipStatus.valueOf(updates.get("ownershipStatus")));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Estado de propiedad no válido");
            }
        }

        Game updated = gameRepository.save(game);
        return GameMapper.toResponseDTO(updated);
    }

    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RuntimeException("Usuario no autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof com.pm.login.security.UserDetailsImpl userDetails) {
            return userDetails.getUsuario();
        } else {
            throw new RuntimeException("Principal no contiene un usuario válido");
        }
    }
}
