package com.softeer.batch.forecast.shortterm.writer;

import com.softeer.batch.forecast.shortterm.writersupporter.ShortForecastWriterSupporter;
import com.softeer.domain.Forecast;
import com.softeer.time.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@StepScope
public class ScheduledShortForecastWriter extends AbstractShortForecastWriter {

    public ScheduledShortForecastWriter(ShortForecastWriterSupporter shortForecastWriterSupporter) {
        super(shortForecastWriterSupporter);
    }

    @Override
    protected List<Forecast> filterForecasts(List<Forecast> forecasts) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime sixHoursLater = now.plusHours(6);
        final LocalDateTime rawThreeDaysLater = now.plusDays(3).withHour(0);
        final LocalDateTime threeDaysLater = TimeUtil.getBaseTime(rawThreeDaysLater);

        return forecasts.stream()
                .filter(hourly -> hourly.dateTime().isAfter(sixHoursLater) && !hourly.dateTime().isAfter(threeDaysLater))
                .collect(Collectors.toList());
    }
}
