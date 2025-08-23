package com.softeer.batch.forecast.shortterm.listener;

import com.softeer.batch.forecast.chained.context.DailyTemperatureContextAccessor;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureKey;
import com.softeer.batch.forecast.chained.dto.DailyTemperatureValue;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DailyTemperatureCollector implements StepExecutionListener {

    private final Map<DailyTemperatureKey, DailyTemperatureValue> buffer = new HashMap<>();

    public void collect(long gridId, LocalDate date, double highest, double lowest) {
        buffer.put(new DailyTemperatureKey(gridId, date), new DailyTemperatureValue(highest, lowest));
    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        DailyTemperatureContextAccessor.put(stepExecution.getJobExecution().getExecutionContext(), buffer);
        return ExitStatus.COMPLETED;
    }
}
