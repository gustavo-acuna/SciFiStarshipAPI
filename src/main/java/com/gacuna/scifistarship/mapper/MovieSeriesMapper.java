package com.gacuna.scifistarship.mapper;

import com.gacuna.scifistarship.dto.MovieSeriesDto;
import com.gacuna.scifistarship.model.MovieSeriesEntity;

import java.util.HashSet;

public class MovieSeriesMapper {
    private MovieSeriesMapper() {
    }

    public static MovieSeriesDto map(MovieSeriesEntity entity) {
        return MovieSeriesDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .build();
    }

    public static MovieSeriesEntity map(MovieSeriesDto dto) {
        return MovieSeriesEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .starShips(new HashSet<>())
                .build();
    }
}
