package com.considlia.tictac.utils;

import java.time.Duration;
import java.util.List;

public class TicTacUtils {

    public static String getDurationAsHHMMSS(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String getDurationAsHHMM(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return String.format("%d hours and %d minutes", hours, minutes);
    }

    public static Duration sumDuration(List<Duration> durationList) {
        return durationList.stream().reduce(Duration::plus).get();
    }
}
