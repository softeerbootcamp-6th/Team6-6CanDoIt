package com.softeer.batch.forecast.mountain.writer;

import com.softeer.batch.common.writersupporter.ForecastJdbcWriter;
import com.softeer.batch.common.writersupporter.SunTimeJdbcWriter;
import com.softeer.domain.Forecast;
import com.softeer.time.TimeUtil;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@StepScope
public class ScheduledMountainForecastWriter extends AbstractMountainForecastWriter {

    public ScheduledMountainForecastWriter(ForecastJdbcWriter forecastJdbcWriter, SunTimeJdbcWriter sunTimeJdbcWriter) {
        super(forecastJdbcWriter, sunTimeJdbcWriter);
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
