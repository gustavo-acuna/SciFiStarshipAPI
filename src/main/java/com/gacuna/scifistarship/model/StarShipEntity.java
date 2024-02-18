package com.gacuna.scifistarship.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "starship")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StarShipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToMany
    @JoinTable(name = "starship_movie_series",
            joinColumns = @JoinColumn(name = "starship_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_series_id"))
    private Set<MovieSeriesEntity> appearances = new HashSet<>();
}
