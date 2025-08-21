package com.softeer.batch.forecast.support;

public final class BatchNames {

    private BatchNames() {}

    public static final class Jobs {
        // chained forecast jobs
        public static final String STARTUP_CHAINED_FORECAST_JOB = "startupChainedForecastJob";
        public static final String SCHEDULED_CHAINED_FORECAST_JOB = "scheduledChainedForecastJob";
    }

    public static final class Steps {
        // mountain forecast steps
        public static final String STARTUP_MOUNTAIN_FORECAST_STEP = "startupMountainForecastStep";
        public static final String SCHEDULED_MOUNTAIN_FORECAST_STEP = "scheduledMountainForecastStep";

        // short forecast steps
        public static final String START_UP_SHORT_FORECAST_STEP = "StartUpShortForecastStep";
        public static final String SCHEDULED_SHORT_FORECAST_STEP = "ScheduledShortForecastStep";
    }

    public static final class Handlers {
        public static final String SHORT_SIMPLE_RETRY_HANDLER = "ShortSimpleRetryHandler";
    }
}
