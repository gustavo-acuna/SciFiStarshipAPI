package com.gacuna.scifistarship.service;

import com.gacuna.scifistarship.dto.StarShipDto;
import com.gacuna.scifistarship.exception.FunctionalException;
import com.gacuna.scifistarship.mapper.StarShipMapper;
import com.gacuna.scifistarship.model.MovieSeriesEntity;
import com.gacuna.scifistarship.model.StarShipEntity;
import com.gacuna.scifistarship.repository.StarShipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class StarShipService {
    private final StarShipRepository starShipRepository;

    private final MovieSeriesService movieSeriesService;


    public Page<StarShipDto> getAllStarShips(Pageable pageRequest) {
        return starShipRepository.findAll(pageRequest)
                .map(StarShipMapper::map);
    }

    @Cacheable("starShipsById")
    public StarShipDto getStarShipById(Long id) {
        return starShipRepository.findById(id)
                .map(StarShipMapper::map)
                .orElseThrow(() -> new FunctionalException("Starship not found with id: " + id));
    }

    public StarShipDto createStarShip(StarShipDto starShip) {
        StarShipEntity entity = StarShipMapper.map(starShip);
        Set<MovieSeriesEntity> savedMovies = movieSeriesService.saveOrUpdateMovies(entity, starShip.getAppearances());
        entity.setAppearances(savedMovies);

        return StarShipMapper.map(starShipRepository.save(entity));
    }


    public StarShipDto updateStarShip(Long id, StarShipDto updatedStarShip) {
        StarShipEntity entity = starShipRepository.findById(id).orElseThrow(() -> new FunctionalException("Starship not found with id: " + id));
        entity.setName(updatedStarShip.getName());

        Set<MovieSeriesEntity> savedMovies = movieSeriesService.saveOrUpdateMovies(entity, updatedStarShip.getAppearances());
        entity.setAppearances(savedMovies);

        StarShipEntity saved = starShipRepository.save(entity);
        return StarShipMapper.map(saved);
    }


    public void deleteStarShip(Long id) {
        starShipRepository.deleteById(id);
    }

    @Cacheable("starShipsByNameSearch")
    public List<StarShipDto> searchStarShipsByNameContaining(String name) {
        return starShipRepository.findByNameContainingOrderByNameAsc(name)
                .stream()
                .map(StarShipMapper::map)
                .toList();
    }
}
