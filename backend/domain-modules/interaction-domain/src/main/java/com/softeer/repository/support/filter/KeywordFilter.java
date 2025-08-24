package com.softeer.repository.support.filter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KeywordFilter  {

    private final List<Integer> weatherKeywordIds;
    private final List<Integer> rainKeywordIds;
    private final List<Integer> etceteraKeywordIds;

    public KeywordFilter(List<Integer> weatherKeywordIds,
                         List<Integer> rainKeywordIds,
                         List<Integer> etceteraKeywordIds) {
        this.weatherKeywordIds  = Objects.isNull(weatherKeywordIds)  ? Collections.emptyList() : weatherKeywordIds;
        this.rainKeywordIds     = Objects.isNull(rainKeywordIds)     ? Collections.emptyList() : rainKeywordIds;
        this.etceteraKeywordIds = Objects.isNull(etceteraKeywordIds) ? Collections.emptyList() : etceteraKeywordIds;
    }

    public List<Integer> weatherKeywordIds() {
        return weatherKeywordIds;
    }

    public List<Integer> rainKeywordIds() {
        return rainKeywordIds;
    }

    public List<Integer> etceteraKeywordIds() {
        return etceteraKeywordIds;
    }
}
