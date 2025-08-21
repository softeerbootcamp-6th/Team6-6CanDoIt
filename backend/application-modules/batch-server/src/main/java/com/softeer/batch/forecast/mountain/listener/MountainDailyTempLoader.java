package com.softeer.batch.forecast.mountain.listener;

import com.softeer.batch.forecast.chained.context.DailyTempContextAccessor;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureKey;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureValue;
import lombok.Getter;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

@Component
@StepScope
public class MountainDailyTempLoader implements StepExecutionListener {

    @Getter
    private Map<DailyTemperatureKey, DailyTemperatureValue> dailyTempMap = Collections.emptyMap();

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.dailyTempMap = DailyTempContextAccessor.get(stepExecution.getJobExecution().getExecutionContext());
    }

    public DailyTemperatureValue find(long gridId, LocalDate date) {
        return dailyTempMap.get(new DailyTemperatureKey(gridId, date));
    }
}
