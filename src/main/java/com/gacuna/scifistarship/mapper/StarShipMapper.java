package com.gacuna.scifistarship.mapper;

import com.gacuna.scifistarship.dto.StarShipDto;
import com.gacuna.scifistarship.model.StarShipEntity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StarShipMapper {
    private StarShipMapper() {
    }

    public static StarShipDto map(StarShipEntity entity) {
        return StarShipDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .appearances(Optional.ofNullable(entity.getAppearances())
                        .map(movieSeriesEntities -> movieSeriesEntities.stream()
                                .map(MovieSeriesMapper::map)
                                .collect(Collectors.toSet()))
                        .orElse(Set.of()))
                .build();
    }

    public static StarShipEntity map(StarShipDto dto) {
        return StarShipEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .appearances(Optional.ofNullable(dto.getAppearances())
                        .map(movieSeriesDtos -> movieSeriesDtos.stream()
                                .map(MovieSeriesMapper::map)
                                .collect(Collectors.toSet()))
                        .orElse(Set.of()))
                .build();
    }
}
