package com.softeer.common;

import java.util.List;

public interface KmaApiCaller<R> {

    <T extends ApiRequest> List<R> call(T request);
}

