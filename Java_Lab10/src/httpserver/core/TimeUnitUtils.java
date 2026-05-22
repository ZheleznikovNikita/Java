package httpserver.core;

import java.util.concurrent.TimeUnit;

public class TimeUnitUtils {
    private TimeUnitUtils() {
    }

    public static long toMillis(long nanos) {
        return TimeUnit.NANOSECONDS.toMillis(nanos);
    }
}
