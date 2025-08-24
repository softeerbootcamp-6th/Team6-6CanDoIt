package com.softeer.batch.forecast.mountain.listener;

import com.softeer.batch.forecast.chained.context.DailyTemperatureContextAccessor;
import com.softeer.batch.forecast.chained.context.ExecutionContextKeys;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureKey;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@StepScope
public class DailyTemperatureLoader implements StepExecutionListener {

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        Map<DailyTemperatureKey, DailyTemperatureValue> dailyTempMap =
                DailyTemperatureContextAccessor.get(stepExecution.getJobExecution().getExecutionContext());

        ExecutionContext stepContext = stepExecution.getExecutionContext();
        stepContext.put(ExecutionContextKeys.DAILY_TEMP_MAP, new HashMap<>(dailyTempMap));
    }
}
