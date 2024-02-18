package com.gacuna.scifistarship.repository;

import com.gacuna.scifistarship.model.MovieSeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSeriesRepository extends JpaRepository<MovieSeriesEntity, Long> {
}

