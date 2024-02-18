package com.gacuna.scifistarship.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie_series")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieSeriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "appearances")
    private Set<StarShipEntity> starShips = new HashSet<>();

}

