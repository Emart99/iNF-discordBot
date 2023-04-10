package com.github.ezeakel.infBot.Utils;

import java.util.Arrays;
import java.util.List;

public class TimeParser {
    public static String fromLongToHoursMinutesSeconds(Long value){
        long timeDifference = value/1000;
        int h = (int) (timeDifference / (3600));
        int m = (int) ((timeDifference - (h * 3600)) / 60);
        int s = (int) (timeDifference - (h * 3600) - m * 60);
        return String.format("%02d:%02d:%02d", h,m,s);
    }

    public static Long fronStringToLong(String time){
        List<String> timeList = Arrays.asList(time.split(":"));
        Long longTimeInMillis =
                Long.parseLong(timeList.get(0)) *3600000 +
                        Long.parseLong(timeList.get(1)) * 60000 +
                        Long.parseLong(timeList.get(2)) * 1000;
        return longTimeInMillis;
    }
}
