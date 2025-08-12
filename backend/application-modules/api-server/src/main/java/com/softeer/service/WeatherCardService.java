package com.softeer.service;

import com.softeer.domain.Course;
import com.softeer.domain.CoursePlan;
import com.softeer.domain.Grid;
import com.softeer.domain.Mountain;
import com.softeer.dto.response.card.CourseCardResponse;
import com.softeer.dto.response.card.ForecastCardResponse;
import com.softeer.dto.response.HourlyWeatherResponse;
import com.softeer.dto.response.card.MountainCardResponse;
import com.softeer.time.HikingTime;
import com.softeer.time.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    public MountainCardResponse createMountainCard(long mountainId) {
        LocalDateTime baseTime = TimeUtil.getBaseTime(LocalDateTime.now());

        Mountain mountain = mountainUseCase.getMountainById(mountainId);
        ForecastUseCase.WeatherCondition gridWeatherCondition = forecastUseCase.findForecastWeatherCondition(mountain.grid(), baseTime);

        return new MountainCardResponse(mountain, gridWeatherCondition);
    }

    public CourseCardResponse createCourseCard(long courseId, LocalDateTime dateTime) {
        LocalDateTime baseTime = TimeUtil.getBaseTime(dateTime);

        CoursePlan coursePlan = mountainUseCase.getCoursePlan(courseId, LocalDate.from(dateTime));

        Grid mountainGrid = coursePlan.mountain().grid();
        ForecastUseCase.WeatherCondition weatherCondition = forecastUseCase.findForecastWeatherCondition(mountainGrid, baseTime);

        return new CourseCardResponse(coursePlan.course(), weatherCondition);
    }

    public ForecastCardResponse createForecastCard(long courseId, LocalDateTime startDateTime) {
        CoursePlan coursePlan = mountainUseCase.getCoursePlan(courseId, LocalDate.from(startDateTime));

        Course course = coursePlan.course();

        Grid startGrid = course.startGrid();
        Grid destinationGrid = course.destinationGrid();
        double totalDuration = course.totalDuration();

        HikingTime hikingTime = TimeUtil.getHikingTime(startDateTime, totalDuration);

        int courseAltitude = course.altitude();
        int mountainAltitude = coursePlan.mountain().altitude();

        ForecastUseCase.CourseForecast courseForecast = forecastUseCase.findForecastsByHikingTime(startGrid, destinationGrid, hikingTime);

        return ForecastCardResponse.from(courseForecast, courseAltitude, mountainAltitude);
    }
}
