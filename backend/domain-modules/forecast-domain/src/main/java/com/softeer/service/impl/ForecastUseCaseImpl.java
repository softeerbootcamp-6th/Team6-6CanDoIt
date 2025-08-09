package com.softeer.service.impl;

import com.softeer.domain.Forecast;
import com.softeer.domain.Grid;
import com.softeer.repository.ForecastAdapter;
import com.softeer.service.ForecastUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastUseCaseImpl implements ForecastUseCase {

    private final ForecastAdapter forecastAdapter;

    @Override
    public List<Forecast> findForecastsFromNow(final Grid grid) {
        //TODO 명규님이 시간 계산 유틸 클래스 작성해주시면 그때 UseCase 완성하기
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        return forecastAdapter.findForecastsAfterStartTime(grid.id(), now);
    }
}
