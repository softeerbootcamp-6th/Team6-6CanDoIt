package com.softeer.team_6th.service;

import com.softeer.team_6th.domain.Grid;
import com.softeer.team_6th.entity.GridEntity;
import com.softeer.team_6th.repository.GridRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * GridService 테스트
 */
@ExtendWith(MockitoExtension.class)
class GridServiceTest {

    @Mock
    private GridRepository gridRepository;
    
    @InjectMocks
    private GridService gridService;

    @Test
    void 모든_그리드_조회_테스트() {
        // given
        GridEntity entity1 = GridEntity.builder()
                .nx(60)
                .ny(127)
                .regionName("서울")
                .build();
        GridEntity entity2 = GridEntity.builder()
                .nx(61)
                .ny(128)
                .regionName("부산")
                .build();
        
        when(gridRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));
        
        // when
        List<Grid> grids = gridService.findAllGrids();
        
        // then
        assertThat(grids).hasSize(2);
        assertThat(grids.get(0).getRegionName()).isEqualTo("서울");
        assertThat(grids.get(1).getRegionName()).isEqualTo("부산");
    }

    @Test
    void 격자_좌표로_그리드_조회_테스트() {
        // given
        GridEntity entity = GridEntity.builder()
                .nx(60)
                .ny(127)
                .regionName("서울")
                .build();
        
        when(gridRepository.findByNxAndNy(60, 127)).thenReturn(Optional.of(entity));
        
        // when
        Optional<Grid> result = gridService.findGridByCoordinates(60, 127);
        
        // then
        assertThat(result).isPresent();
        assertThat(result.get().getNx()).isEqualTo(60);
        assertThat(result.get().getNy()).isEqualTo(127);
        assertThat(result.get().getRegionName()).isEqualTo("서울");
    }

    @Test
    void 그리드_저장_테스트() {
        // given
        Grid grid = Grid.builder()
                .nx(60)
                .ny(127)
                .regionName("서울")
                .build();
        
        GridEntity savedEntity = GridEntity.builder()
                .nx(60)
                .ny(127)
                .regionName("서울")
                .build();
        
        when(gridRepository.save(any(GridEntity.class))).thenReturn(savedEntity);
        
        // when
        Grid result = gridService.saveGrid(grid);
        
        // then
        assertThat(result.getNx()).isEqualTo(60);
        assertThat(result.getNy()).isEqualTo(127);
        assertThat(result.getRegionName()).isEqualTo("서울");
    }
}