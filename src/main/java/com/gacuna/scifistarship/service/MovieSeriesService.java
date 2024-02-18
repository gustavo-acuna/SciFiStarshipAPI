package com.gacuna.scifistarship.service;

import com.gacuna.scifistarship.dto.MovieSeriesDto;
import com.gacuna.scifistarship.exception.FunctionalException;
import com.gacuna.scifistarship.mapper.MovieSeriesMapper;
import com.gacuna.scifistarship.model.MovieSeriesEntity;
import com.gacuna.scifistarship.model.StarShipEntity;
import com.gacuna.scifistarship.repository.MovieSeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieSeriesService {
    private final MovieSeriesRepository movieSeriesRepository;


    public Set<MovieSeriesEntity> saveOrUpdateMovies(final StarShipEntity starShipEntity, final Set<MovieSeriesDto> appearances) {
        return Optional.ofNullable(appearances)
                .map(movies -> movies.stream()
                        .map(movieDto -> toEntity(movieDto, starShipEntity))
                        .map(movieSeriesRepository::save)
                        .collect(Collectors.toSet()))
                .orElse(Set.of());
    }

    private MovieSeriesEntity toEntity(final MovieSeriesDto movieDto, final StarShipEntity starShipEntity) {
        MovieSeriesEntity movieEntity = Optional.ofNullable(movieDto.getId())
                .map(id -> movieSeriesRepository.findById(movieDto.getId())
                        .orElseThrow(() -> new FunctionalException("Movie not found with id: " + movieDto.getId())))
                .orElseGet(() -> MovieSeriesMapper.map(movieDto));
        movieEntity.setTitle(movieDto.getTitle());
        movieEntity.getStarShips().add(starShipEntity);
        return movieEntity;
    }
}