package com.gacuna.scifistarship.controller;

import com.gacuna.scifistarship.dto.StarShipDto;
import com.gacuna.scifistarship.service.StarShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/starships")
@Tag(name = "StarShips", description = "Endpoints para el mantenimiento de naves espaciales")
@RequiredArgsConstructor
public class StarShipController {


    private final StarShipService starShipService;

    @GetMapping
    @Operation(summary = "Obtener todas las naves espaciales")
    public ResponseEntity<Page<StarShipDto>> getAllStarShips(Pageable pageRequest) {
        Page<StarShipDto> starShips = starShipService.getAllStarShips(pageRequest);
        return new ResponseEntity<>(starShips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una nave espacial por su Id")
    public ResponseEntity<StarShipDto> getStarShipById(@PathVariable Long id) {
        StarShipDto starShip = starShipService.getStarShipById(id);
        return new ResponseEntity<>(starShip, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva nave espacial")
    public ResponseEntity<StarShipDto> createStarShip(@RequestBody StarShipDto starShip) {
        StarShipDto createdStarShip = starShipService.createStarShip(starShip);
        return new ResponseEntity<>(createdStarShip, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una nave espacial existente")
    public ResponseEntity<StarShipDto> updateStarShip(@PathVariable Long id, @RequestBody StarShipDto starShip) {
        StarShipDto updatedStarShip = starShipService.updateStarShip(id, starShip);
        return new ResponseEntity<>(updatedStarShip, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una nave espacial")
    public ResponseEntity<Void> deleteStarShip(@PathVariable Long id) {
        starShipService.deleteStarShip(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar todas las naves espaciales que contienen en su nombre, el valor del parámetro enviado")
    public ResponseEntity<List<StarShipDto>> searchStarShipsByNameContaining(@RequestParam String name) {
        List<StarShipDto> starShips = starShipService.searchStarShipsByNameContaining(name);
        return ResponseEntity.ok(starShips);
    }
}
