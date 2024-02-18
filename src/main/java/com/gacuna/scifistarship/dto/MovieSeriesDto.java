package com.gacuna.scifistarship.dto;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MovieSeriesDto implements Serializable {

    private Long id;

    private String title;
}
