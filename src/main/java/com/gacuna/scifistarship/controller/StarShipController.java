package com.gacuna.scifistarship.controller;

import com.gacuna.scifistarship.dto.StarShipDto;
import com.gacuna.scifistarship.service.StarShipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/starships")
@RequiredArgsConstructor
public class StarShipController {


    private final StarShipService starShipService;

    @GetMapping
    public ResponseEntity<List<StarShipDto>> getAllStarShips() {
        List<StarShipDto> starShips = starShipService.getAllStarShips();
        return new ResponseEntity<>(starShips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StarShipDto> getStarShipById(@PathVariable Long id) {
        StarShipDto starShip = starShipService.getStarShipById(id);
        return new ResponseEntity<>(starShip, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StarShipDto> createStarShip(@RequestBody StarShipDto starShip) {
        StarShipDto createdStarShip = starShipService.createStarShip(starShip);
        return new ResponseEntity<>(createdStarShip, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StarShipDto> updateStarShip(@PathVariable Long id, @RequestBody StarShipDto starShip) {
        StarShipDto updatedStarShip = starShipService.updateStarShip(id, starShip);
        return new ResponseEntity<>(updatedStarShip, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStarShip(@PathVariable Long id) {
        starShipService.deleteStarShip(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
