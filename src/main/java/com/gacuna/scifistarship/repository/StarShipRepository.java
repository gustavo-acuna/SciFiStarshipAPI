package com.gacuna.scifistarship.repository;

import com.gacuna.scifistarship.model.StarShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StarShipRepository extends JpaRepository<StarShipEntity, Long> {

    List<StarShipEntity> findByNameContainingOrderByNameAsc(String name);
}
