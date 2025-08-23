package com.softeer.service;

import com.softeer.activity.HikingActivityCalculator;
import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.domain.Grid;
import com.softeer.domain.Mountain;
import com.softeer.dto.response.CourseInfoResponse;
import com.softeer.dto.response.card.*;
import com.softeer.dto.response.HourlyWeatherResponse;
import com.softeer.time.HikingTime;
import com.softeer.time.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherCardService {

    private final MountainUseCase mountainUseCase;
    private final ForecastUseCase forecastUseCase;

    public List<HourlyWeatherResponse> findForecastsByMountainId(long mountainId, LocalDateTime startDatetime) {
        Mountain mountain = mountainUseCase.getMountainById(mountainId);
        LocalDateTime startBaseDateTime = TimeUtil.getBaseTime(startDatetime);

        return forecastUseCase.findForecastsFromStartDateTime(mountain.grid(), startBaseDateTime)
                .stream()
                .map((HourlyWeatherResponse::new))
                .toList();
    }

    public List<CourseInfoResponse> findCoursesByMountainId(long mountainId) {
        List<Course> courses = mountainUseCase.getCoursesByMountainId(mountainId);

        return courses.stream()
                .map(CourseInfoResponse::new)
                .toList();
    }

    public List<MountainCardResponse> createMountainCards() {
        LocalDateTime baseTime = TimeUtil.getBaseTime(LocalDateTime.now());

        Map<Integer, Mountain> mountainMap = mountainUseCase.getMountains()
                .stream()
                .collect(Collectors.toMap(mountain -> mountain.grid().id(), Function.identity()));

        List<Integer> gridIds = mountainMap.keySet().stream().toList();

        Map<Integer, ForecastUseCase.WeatherCondition> weatherConditionMap = forecastUseCase.findAllWeatherConditions(gridIds, baseTime);

        if(mountainMap.size() != weatherConditionMap.size()) throw new RuntimeException();

        List<MountainCardResponse> mountainCardResponses  = new ArrayList<>();

        for (Integer gridId : mountainMap.keySet()) {
            Mountain mountain = mountainMap.get(gridId);
            ForecastUseCase.WeatherCondition weatherCondition = weatherConditionMap.get(gridId);

            mountainCardResponses.add(new MountainCardResponse(mountain, weatherCondition));
        }
        return mountainCardResponses;
    }

    public CourseCardResponse createCourseCard(long courseId, LocalDateTime dateTime) {
        LocalDateTime baseTime = TimeUtil.getBaseTime(dateTime);

        CoursePlan coursePlan = mountainUseCase.getCoursePlan(courseId, LocalDate.from(dateTime));

        Grid mountainGrid = coursePlan.mountain().grid();
        ForecastUseCase.WeatherCondition weatherCondition = forecastUseCase.findForecastWeatherCondition(mountainGrid, baseTime);

        return new CourseCardResponse(coursePlan.course(), coursePlan.sunTime(), weatherCondition);
    }

    public ForecastCardResponse createForecastCard(long courseId, LocalDateTime startDateTime) {
        CoursePlan coursePlan = mountainUseCase.getCoursePlan(courseId, LocalDate.from(startDateTime));

        Course course = coursePlan.course();

        Grid startGrid = course.startGrid();
        Grid destinationGrid = coursePlan.mountain().grid();
        double totalDuration = course.totalDuration();

        HikingTime hikingTime = TimeUtil.getHikingTime(startDateTime, totalDuration);

        int courseAltitude = course.altitude();
        int mountainAltitude = coursePlan.mountain().altitude();

        ForecastUseCase.CourseForecast courseForecast = forecastUseCase.findForecastsByHikingTime(startGrid, destinationGrid, hikingTime);

        return ForecastCardResponse.from(courseForecast, courseAltitude, mountainAltitude);
    }

    public MountainCourseCardResponse createMountainCourseCard(long courseId, LocalDateTime dateTime) {
        CoursePlan coursePlan = mountainUseCase.getCoursePlan(courseId, LocalDate.from(dateTime));

        return new MountainCourseCardResponse(
                coursePlan.mountain(),
                coursePlan.course(),
                coursePlan.sunTime()
        );
    }

    public CourseScheduleCardResponse createCourseScheduleCard(Long courseId, LocalDateTime startDateTime) {
        CoursePlan coursePlan = mountainUseCase.getCoursePlan(courseId, LocalDate.from(startDateTime));

        HikingTime hikingTime = TimeUtil.getHikingTime(startDateTime, coursePlan.course().totalDuration());

        ForecastUseCase.CourseForecast forecasts = forecastUseCase.findForecastsByHikingTime(
                coursePlan.course().startGrid(),
                coursePlan.mountain().grid(),
                hikingTime
        );

        int courseAltitude = coursePlan.course().altitude();
        int mountainAltitude = coursePlan.mountain().altitude();

        double adjustedTemperature = HikingActivityCalculator.calculateTemperatureWithAltitude(
                forecasts.arrivalForecast().temperatureCondition().temperature(),
                courseAltitude,
                mountainAltitude
        );

        double adjustedWindSpeed = HikingActivityCalculator.calculateWindSpeedWithAltitude(
                forecasts.arrivalForecast().windCondition().windSpeed(),
                courseAltitude,
                mountainAltitude
        );

        String hikingActivityStatus = HikingActivityCalculator.calculateHikingActivity(
                adjustedTemperature,
                forecasts.arrivalForecast().dailyTemperature().dailyTemperatureRange(),
                forecasts.arrivalForecast().precipitationCondition().precipitation(),
                adjustedWindSpeed,
                forecasts.arrivalForecast().humidityCondition().humidity()
        );

        return new CourseScheduleCardResponse(
                coursePlan,
                hikingTime,
                forecasts.startForecast(),
                forecasts.arrivalForecast(),
                forecasts.descentForecast(),
                hikingActivityStatus
        );
    }
}
