package com.gacuna.scifistarship.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
public class StarShipDto implements Serializable {
    private Long id;

    private String name;

    private Set<MovieSeriesDto> appearances;
}
