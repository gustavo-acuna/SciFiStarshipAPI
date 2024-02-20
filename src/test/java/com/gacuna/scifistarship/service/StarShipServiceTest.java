package com.gacuna.scifistarship.service;

import com.gacuna.scifistarship.dto.MovieSeriesDto;
import com.gacuna.scifistarship.dto.StarShipDto;
import com.gacuna.scifistarship.exception.FunctionalException;
import com.gacuna.scifistarship.model.MovieSeriesEntity;
import com.gacuna.scifistarship.model.StarShipEntity;
import com.gacuna.scifistarship.repository.StarShipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StarShipServiceTest {
    @Mock
    private StarShipRepository starShipRepository;
    @Mock
    private MovieSeriesService movieSeriesService;
    @InjectMocks
    private StarShipService starShipService;

    @Test
    void getAllStarShips_ReturnsPageOfStarShipDto() {
        // Given
        Pageable pageRequest = Pageable.ofSize(1);
        MovieSeriesEntity movieSeriesEntity = MovieSeriesEntity.builder().id(1L).title("Star Wars").build();
        Set<MovieSeriesEntity> movieSeriesEntitySet = Set.of(movieSeriesEntity);
        StarShipEntity shipEntity = StarShipEntity.builder().id(1L).name("X-Wing").appearances(movieSeriesEntitySet).build();
        Page<StarShipEntity> mockPage = new PageImpl<>(Collections.singletonList(shipEntity), pageRequest, 1);

        when(starShipRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        // When
        Page<StarShipDto> result = starShipService.getAllStarShips(pageRequest);

        // Then
        verify(starShipRepository, times(1)).findAll(pageRequest);
        assertNotNull(result);
        assertTrue(result.hasContent());
        assertEquals(1, result.getSize());
        StarShipDto starShipDto = result.getContent().getFirst();
        assertStarShipAndAppearances(starShipDto, shipEntity, movieSeriesEntity);
    }


    @Test
    void getStarShipById_WhenStarShipExists_ReturnsStarShipDto() {
        //  Given
        long id = 1L;
        MovieSeriesEntity movieSeriesEntity = MovieSeriesEntity.builder().id(id).title("Star Wars").build();
        Set<MovieSeriesEntity> movieSeriesEntitySet = Set.of(movieSeriesEntity);
        StarShipEntity shipEntity = StarShipEntity.builder().id(id).name("X-Wing").appearances(movieSeriesEntitySet).build();
        when(starShipRepository.findById(anyLong())).thenReturn(Optional.of(shipEntity));

        // When
        StarShipDto resultDto = starShipService.getStarShipById(id);

        // Then
        assertNotNull(resultDto);
        assertStarShipAndAppearances(resultDto, shipEntity, movieSeriesEntity);

        verify(starShipRepository, times(1)).findById(id);
    }

    @Test
    void getStarShipById_WhenStarShipDoesNotExist_ThrowsFunctionalException() {
        //Given
        Long id = 1L;
        when(starShipRepository.findById(id)).thenReturn(Optional.empty());

        // When
        assertThrows(FunctionalException.class, () -> starShipService.getStarShipById(id));

        // Then
        verify(starShipRepository, times(1)).findById(id);
    }

    @Test
    void createStarShip_ReturnsStarShipDto() {
        // Given
        MovieSeriesDto movieSeriesDto = MovieSeriesDto.builder().id(1L).title("Star Wars").build();
        Set<MovieSeriesDto> movieSeriesDtoSet = Set.of(movieSeriesDto);
        StarShipDto starShipDto = StarShipDto.builder().name("X-Wing").appearances(movieSeriesDtoSet).build();

        MovieSeriesEntity movieSeriesEntity = MovieSeriesEntity.builder().id(1L).title("Star Wars").build();
        Set<MovieSeriesEntity> movieSeriesEntitySet = Set.of(movieSeriesEntity);
        StarShipEntity shipEntity = StarShipEntity.builder().id(1L).name("X-Wing").appearances(movieSeriesEntitySet).build();

        when(movieSeriesService.saveOrUpdateMovies(any(StarShipEntity.class), anySet())).thenReturn(movieSeriesEntitySet);
        when(starShipRepository.save(any(StarShipEntity.class))).thenReturn(shipEntity);

        // When
        StarShipDto resultDto = starShipService.createStarShip(starShipDto);

        // Then
        assertNotNull(resultDto);
        assertStarShipAndAppearances(resultDto, shipEntity, movieSeriesEntity);
        verify(starShipRepository, times(1)).save(any(StarShipEntity.class));
    }

    @Test
    void updateStarShip_WhenStarShipExists_ReturnsUpdatedStarShipDto() {
        // Given
        Long id = 1L;
        MovieSeriesDto movieSeriesDto = MovieSeriesDto.builder().id(1L).title("Star Wars").build();
        Set<MovieSeriesDto> movieSeriesDtoSet = Set.of(movieSeriesDto);
        StarShipDto starShipDto = StarShipDto.builder().id(id).name("A-Wing").appearances(movieSeriesDtoSet).build();

        MovieSeriesEntity movieSeriesEntity = MovieSeriesEntity.builder().id(1L).title("Star Wars").build();
        Set<MovieSeriesEntity> movieSeriesEntitySet = Set.of(movieSeriesEntity);

        StarShipEntity existingEntity = StarShipEntity.builder().id(id).name("X-Wing").appearances(movieSeriesEntitySet).build();
        StarShipEntity savedEntity = StarShipEntity.builder().id(id).name("A-Wing").appearances(movieSeriesEntitySet).build();

        when(starShipRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(starShipRepository.save(any(StarShipEntity.class))).thenReturn(savedEntity);

        // When
        StarShipDto resultDto = starShipService.updateStarShip(id, starShipDto);

        // Then
        assertNotNull(resultDto);
        verify(starShipRepository, times(1)).findById(id);

        ArgumentCaptor<StarShipEntity> entityCaptor = ArgumentCaptor.forClass(StarShipEntity.class);
        verify(starShipRepository, times(1)).save(entityCaptor.capture());
        StarShipEntity savedEntityArgument = entityCaptor.getValue();
        assertEquals(starShipDto.getName(), savedEntityArgument.getName());
    }

    @Test
    void updateStarShip_WhenStarShipDoesNotExist_ThrowsFunctionalException() {
        // Given
        Long id = 1L;
        MovieSeriesDto movieSeriesDto = MovieSeriesDto.builder().id(1L).title("Star Wars").build();
        Set<MovieSeriesDto> movieSeriesDtoSet = Set.of(movieSeriesDto);
        StarShipDto starShipDto = StarShipDto.builder().id(id).name("A-Wing").appearances(movieSeriesDtoSet).build();

        when(starShipRepository.findById(id)).thenReturn(Optional.empty());

        // When
        assertThrows(FunctionalException.class, () -> starShipService.updateStarShip(id, starShipDto));

        // Then
        verify(starShipRepository, times(1)).findById(id);
        verify(starShipRepository, never()).save(any());
    }

    @Test
    void deleteStarShip_WhenStarShipExists_DeletesSuccessfully() {
        // Given
        Long id = 1L;

        // When
        starShipService.deleteStarShip(id);

        // Then
        verify(starShipRepository, times(1)).deleteById(id);
    }

    @Test
    void searchStarShipsByNameContaining_ReturnsMatchingStarShips() {
        // Given
        String name = "Wing";
        MovieSeriesEntity movieSeriesEntity = MovieSeriesEntity.builder().id(1L).title("Star Wars").build();
        Set<MovieSeriesEntity> movieSeriesEntitySet = Set.of(movieSeriesEntity);
        StarShipEntity shipEntity1 = StarShipEntity.builder().id(1L).name("X-Wing").appearances(movieSeriesEntitySet).build();
        StarShipEntity shipEntity2 = StarShipEntity.builder().id(2L).name("Y-Wing").appearances(movieSeriesEntitySet).build();

        when(starShipRepository.findByNameContainingOrderByNameAsc(name)).thenReturn(List.of(shipEntity1, shipEntity2));

        // When
        List<StarShipDto> result = starShipService.searchStarShipsByNameContaining(name);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(starShipRepository, times(1)).findByNameContainingOrderByNameAsc(name);

    }

    private static void assertStarShipAndAppearances(StarShipDto starShipDto, StarShipEntity shipEntity, MovieSeriesEntity movieSeriesEntity) {
        assertAll("Assertions of starShipDto",
                () -> assertEquals(shipEntity.getId(), starShipDto.getId()),
                () -> assertEquals(shipEntity.getName(), starShipDto.getName()),
                () -> assertEquals(shipEntity.getName(), starShipDto.getName())
        );
        assertNotNull(starShipDto.getAppearances());
        assertEquals(shipEntity.getAppearances().size(), starShipDto.getAppearances().size());
        shipEntity.getAppearances().stream().findFirst().ifPresent(movieSeriesDto ->
                assertAll("Assertions of movieSeriesDto",
                        () -> assertEquals(movieSeriesEntity.getId(), movieSeriesDto.getId()),
                        () -> assertEquals(movieSeriesEntity.getTitle(), movieSeriesDto.getTitle())
                )
        );
    }


}