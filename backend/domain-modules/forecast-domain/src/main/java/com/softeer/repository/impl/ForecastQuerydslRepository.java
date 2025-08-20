package com.softeer.repository.impl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.softeer.domain.Forecast;
import com.softeer.entity.enums.ForecastType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.softeer.entity.QDailyTemperatureEntity.*;
import static com.softeer.entity.QForecastEntity.*;
import static com.softeer.entity.QGridEntity.*;

@Repository
@RequiredArgsConstructor
public class ForecastQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Forecast> findForecastsAfterStartDateTime(int gridId, LocalDateTime startDaTeTime,  LocalDateTime endDateTime) {
        DateTemplate<LocalDate> forecastDate = extractForecastDate();

        return selectForecast()
                .innerJoin(gridEntity).on(forecastEntity.gridEntity.id.eq(gridEntity.id))
                .innerJoin(dailyTemperatureEntity).on(
                        gridEntity.id.eq(dailyTemperatureEntity.gridEntity.id),
                        dailyTemperatureEntity.date.eq(forecastDate)
                )
                .where(
                        forecastEntity.gridEntity.id.eq(gridId),
                        forecastEntity.type.eq(ForecastType.SHORT),
                        forecastEntity.dateTime.between(startDaTeTime,  endDateTime)
                )
                .orderBy(forecastEntity.dateTime.asc())
                .fetch();
    }

    public Optional<Forecast> findForecastByTypeAndDateTime(int gridId, ForecastType type, LocalDateTime dateTime) {
        DateTemplate<LocalDate> forecastDate = extractForecastDate();

        return Optional.ofNullable(
                selectForecast()
                        .innerJoin(gridEntity).on(forecastEntity.gridEntity.id.eq(gridEntity.id))
                        .innerJoin(dailyTemperatureEntity).on(
                                gridEntity.id.eq(dailyTemperatureEntity.gridEntity.id),
                                dailyTemperatureEntity.date.eq(forecastDate)
                        )
                        .where(
                                forecastEntity.gridEntity.id.eq(gridId),
                                forecastEntity.dateTime.eq(dateTime),
                                forecastEntity.type.eq(type)
                        ).fetchOne());
    }

    public Map<Integer, Forecast> findForecastsByTypeAndDateTime(List<Integer> gridIds, ForecastType type, LocalDateTime dateTime) {

        DateTemplate<LocalDate> forecastDate = extractForecastDate();

        return queryFactory
                .from(forecastEntity)
                .innerJoin(gridEntity).on(forecastEntity.gridEntity.id.eq(gridEntity.id))
                .innerJoin(dailyTemperatureEntity).on(
                        gridEntity.id.eq(dailyTemperatureEntity.gridEntity.id),
                        dailyTemperatureEntity.date.eq(forecastDate)
                )
                .where(
                        forecastEntity.gridEntity.id.in(gridIds),
                        forecastEntity.dateTime.eq(dateTime),
                        forecastEntity.type.eq(type)
                )
                .transform(
                        GroupBy.groupBy(gridEntity.id).as(
                                Projections.constructor(
                                        Forecast.class,
                                        forecastEntity.id,
                                        forecastEntity.dateTime,
                                        forecastEntity.type,
                                        forecastEntity.sky,
                                        forecastEntity.temperature,
                                        forecastEntity.humidity,
                                        forecastEntity.windDir,
                                        forecastEntity.windSpeed,
                                        forecastEntity.precipitationType,
                                        forecastEntity.precipitation,
                                        forecastEntity.precipitationProbability,
                                        forecastEntity.snowAccumulation,
                                        dailyTemperatureEntity.highestTemperature,
                                        dailyTemperatureEntity.lowestTemperature
                                )
                        )
                );
    }

    private JPAQuery<Forecast> selectForecast() {
        return queryFactory.select(Projections.constructor(
                        Forecast.class,
                        forecastEntity.id,
                        forecastEntity.dateTime,
                        forecastEntity.type,
                        forecastEntity.sky,
                        forecastEntity.temperature,
                        forecastEntity.humidity,
                        forecastEntity.windDir,
                        forecastEntity.windSpeed,
                        forecastEntity.precipitationType,
                        forecastEntity.precipitation,
                        forecastEntity.precipitationProbability,
                        forecastEntity.snowAccumulation,
                        dailyTemperatureEntity.highestTemperature,
                        dailyTemperatureEntity.lowestTemperature
                ))
                .from(forecastEntity);
    }

    private DateTemplate<LocalDate> extractForecastDate() {
        return Expressions.dateTemplate(LocalDate.class, "DATE({0})", forecastEntity.dateTime);
    }
}
