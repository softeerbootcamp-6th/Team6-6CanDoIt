package com.softeer.mountain;

import com.softeer.AbstractKmaApiCaller;
import com.softeer.KmaApiCaller;
import com.softeer.config.ForecastApiType;
import com.softeer.mountain.dto.MountainForecastApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class MountainForecastApiCaller
        extends AbstractKmaApiCaller<MountainForecastApiCaller.Request, MountainForecastApiResponse>
        implements KmaApiCaller<MountainForecastApiCaller.Request, MountainForecastApiResponse> {

    public record Request(
            int mountainNum,
            String base_date,
            String base_time
    ) {}

    public MountainForecastApiCaller(
            @Qualifier("mountainForecastApiWebClient") RestClient restClient,
            @Value("${kma.api.key.mountain}") String apiKey) {
        super(restClient, apiKey, MountainForecastApiResponse.class, ForecastApiType.MOUNTAIN);
    }

    @Override
    protected String getApiPath() {
        return "/api/typ08/getMountainWeather";
    }

    @Override
    public Class<Request> getRequestType() {
        return Request.class;
    }

    @Override
    public List<MountainForecastApiResponse> call(Request request) {
        return super.call(request);
    }
}
