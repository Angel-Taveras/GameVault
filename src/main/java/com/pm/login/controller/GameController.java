package com.pm.login.controller;

import com.pm.login.dto.RequestGameDTO;
import com.pm.login.dto.ResponseGameDTO;
import com.pm.login.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api/games")
@RestController
@Slf4j
@Tag(name = "Games", description = "Operaciones relacionadas con videojuegos")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Guardar nuevo juego", description = "Agrega un juego asociado al usuario autenticado")
    @PostMapping
    public ResponseEntity<ResponseGameDTO> createGame(@Valid @RequestBody RequestGameDTO game) {
        log.info("Recibida petición POST /games");
        ResponseGameDTO gameResponse = gameService.saveGame(game);
        return ResponseEntity.ok(gameResponse);
    }

    @Operation(summary = "Lista de juegos", description = "Visualizar todos los juegos de un usuario")
    @GetMapping
    public ResponseEntity<List<ResponseGameDTO>> getAllGames() {
        log.info("Recibiendo petición de visualización de todos los juegos");
        List<ResponseGameDTO> gameList = gameService.getAllGame();
        return ResponseEntity.ok(gameList);
    }

    @Operation(summary = "Buscar juego en específico", description = "Buscar un juego en específico de la lista")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGameDTO> getGameById(@PathVariable UUID id) {
        log.info("Recibiendo petición para buscar juego por id {}", id);
        ResponseGameDTO gameResponse = gameService.getGameById(id);
        return ResponseEntity.ok(gameResponse);
    }

    @Operation(summary = "Eliminar un juego", description = "Elimina un juego de la lista")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameById(@PathVariable UUID id) {
        log.info("Recibiendo petición para eliminar juego por id {}", id);
        gameService.deleteGameById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar un juego", description = "Actualiza toda la información de un juego")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseGameDTO> updateGame(
            @PathVariable UUID id,
            @Valid @RequestBody RequestGameDTO game) {
        log.info("Recibiendo petición para actualizar juego por id {}", id);
        ResponseGameDTO gameDTO = gameService.updateGameById(id, game);
        return ResponseEntity.ok(gameDTO);
    }

    @Operation(summary = "Actualizar estado de un juego", description = "Actualiza sólo el estado del juego")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseGameDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody Map<String, String> updates) {
        log.info("Recibiendo petición para actualizar status del juego por id {}", id);
        ResponseGameDTO updatedGame = gameService.updateGameStatus(id, updates);
        return ResponseEntity.ok(updatedGame);
    }
}
