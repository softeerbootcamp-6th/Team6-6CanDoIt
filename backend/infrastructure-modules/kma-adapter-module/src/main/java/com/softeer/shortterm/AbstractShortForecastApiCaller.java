package com.softeer.shortterm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.softeer.common.AbstractKmaApiCaller;
import com.softeer.common.ApiRequest;
import com.softeer.config.ForecastApiType;
import com.softeer.shortterm.dto.response.ShortForecastApiResponse;
import com.softeer.shortterm.dto.response.ShortForecastBody;
import com.softeer.shortterm.dto.response.ShortForecastItem;
import com.softeer.throttle.ThrottleException;
import com.softeer.throttle.ThrottleExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public abstract class AbstractShortForecastApiCaller extends AbstractKmaApiCaller<ShortForecastItem> {

    protected AbstractShortForecastApiCaller(RestClient restClient, XmlMapper xmlMapper) {
        super(restClient, ForecastApiType.SHORT_TERM, xmlMapper);
    }
    @Override
    protected String getApiPath() {
        return "";
    }

    @Override
    public <T extends ApiRequest> List<ShortForecastItem> call(T request) {
        URI uri = UriComponentsBuilder
                .fromPath(getApiPath())
                .query(request.queryString())
                .build(true)
                .toUri();

        return restClient.get()
                .uri(uri)
                .exchange((httpRequest, httpResponse) -> {
                    HttpHeaders headers = httpResponse.getHeaders();
                    if(Objects.requireNonNull(headers.getContentType()).includes(MediaType.TEXT_XML)) {
                        ApiErrorResponse errorResponse = xmlMapper.readValue(httpResponse.getBody(), ApiErrorResponse.class);

                        log.error("API Error Response - Code: {}, Message: {}, Reason: {}",
                                errorResponse.getReturnReasonCode(),
                                errorResponse.getErrMsg(),
                                errorResponse.getReturnAuthMsg());

                        // 재시도 가능한 에러인지 체크 (로그만)
                        if (RETRYABLE_ERROR_CODES.contains(errorResponse.getReturnAuthMsg())) {
                            log.warn("This is a retryable error: {}", errorResponse.getReturnAuthMsg());
                            throw new ThrottleException(ThrottleExceptionStatus.RETRY);
                        }
                        throw new  ThrottleException(ThrottleExceptionStatus.NO_RETRY);
                    } else {
                        ShortForecastApiResponse response = httpResponse.bodyTo(ShortForecastApiResponse.class);

                        if (response != null && response.getHeader() != null && "00".equals(response.getHeader().resultCode())) {
                            return Optional.of(response)
                                    .map(ShortForecastApiResponse::getBody)
                                    .map(ShortForecastBody::getItems)
                                    .orElse(Collections.emptyList());
                        } else {
                            return Collections.emptyList();
                        }
                    }
                });
    }

    @JacksonXmlRootElement(localName = "OpenAPI_ServiceResponse")
    private static class ApiErrorResponse {
        @JacksonXmlProperty(localName = "cmmMsgHeader")
        private ErrorHeader cmmMsgHeader;

        public ApiErrorResponse() {}

        public ApiErrorResponse(String errMsg, String returnAuthMsg, String returnReasonCode) {
            this.cmmMsgHeader = new ErrorHeader(errMsg, returnAuthMsg, returnReasonCode);
        }

        public String getErrMsg() {
            return cmmMsgHeader != null ? cmmMsgHeader.errMsg : null;
        }

        public String getReturnAuthMsg() {
            return cmmMsgHeader != null ? cmmMsgHeader.returnAuthMsg : null;
        }

        public String getReturnReasonCode() {
            return cmmMsgHeader != null ? cmmMsgHeader.returnReasonCode : null;
        }

        private static class ErrorHeader {
            @JacksonXmlProperty(localName = "errMsg")
            private String errMsg;

            @JacksonXmlProperty(localName = "returnAuthMsg")
            private String returnAuthMsg;

            @JacksonXmlProperty(localName = "returnReasonCode")
            private String returnReasonCode;

            public ErrorHeader() {}

            public ErrorHeader(String errMsg, String returnAuthMsg, String returnReasonCode) {
                this.errMsg = errMsg;
                this.returnAuthMsg = returnAuthMsg;
                this.returnReasonCode = returnReasonCode;
            }
        }
    }

}
