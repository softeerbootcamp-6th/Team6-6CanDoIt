package com.softeer.batch.forecast.ultra.v3;

import com.softeer.batch.forecast.ultra.AbstractUltraForecastProcessor;
import com.softeer.batch.forecast.ultra.dto.UltraForecastResponseList;
import com.softeer.batch.mapper.ForecastMapper;
import com.softeer.domain.Grid;
import com.softeer.shortterm.UltraForecastApiCaller;
import com.softeer.shortterm.dto.request.ShortForecastApiRequest;
import com.softeer.throttle.manager.AbstractThrottlingManager;
import com.softeer.time.ApiTime;
import com.softeer.time.ApiTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static com.softeer.batch.forecast.ultra.v3.UltraForecastV3JobConfig.*;

@Slf4j
public class UltraForecastV3Processor extends AbstractUltraForecastProcessor implements ItemProcessor<Grid, CompletableFuture<UltraForecastResponseList>> {

    private final ExecutorService executorService;
    private final AbstractThrottlingManager throttlingManager;

    public UltraForecastV3Processor(UltraForecastApiCaller kmaApiCaller,
                                    ForecastMapper forecastMapper,
                                    ExecutorService executorService,
                                    AbstractThrottlingManager throttlingManager) {
        super(kmaApiCaller, forecastMapper);
        this.executorService = executorService;
        this.throttlingManager = throttlingManager;
    }

    @Override
    public CompletableFuture<UltraForecastResponseList> process(Grid grid) throws Exception {
        ApiTime ultraBaseTime = ApiTimeUtil.getUltraBaseTime(dateTime);

        ShortForecastApiRequest request = new ShortForecastApiRequest(
                serviceKey,
                1,
                1500,
                "JSON",
                ultraBaseTime.baseDate(),
                ultraBaseTime.baseTime(),
                grid.x(),
                grid.y()
        );

        return throttlingManager.submit(KEY, () -> CompletableFuture
                .supplyAsync(() -> kmaApiCaller.call(request), executorService)
                .thenApply(response -> processApiResponse(grid, response)));
    }
}
