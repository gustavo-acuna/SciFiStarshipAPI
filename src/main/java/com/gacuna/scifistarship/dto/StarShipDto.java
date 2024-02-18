package com.gacuna.scifistarship.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class StarShipDto {
    private Long id;

    private String name;

    @JsonIgnoreProperties("starShips")
    private Set<MovieSeriesDto> appearances;
}
