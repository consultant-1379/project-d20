package com.ericsson.projectd20.utilities;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Utilities {

    private Utilities()
    {

    }

    public static double validateDouble(double value) {
        if (Double.isNaN(value)) {
            return -1;
        }
        return value;
    }

    public static double standardDeviation(List<Long> diffArray) {
        if(diffArray.size() == 0) return 0L;
        Long sum = 0L;
        double standardDeviation = 0;
        int length = diffArray.size();

        for (Long num : diffArray) {
            sum += num;
        }

        Long mean = sum / length;

        for (Long num : diffArray) {
            long sub = num - mean;

            standardDeviation += Math.pow(sub, 2);
        }

        return Math.sqrt(standardDeviation / diffArray.size());
    }

    public static Long median(List<Long> diffArray) {
        if(diffArray.size() == 0) return 0L;
        Collections.sort(diffArray);
        Long median;
        if (diffArray.size() % 2 == 0) {
            median = (diffArray.get(diffArray.size() / 2) + diffArray.get(diffArray.size() / 2 - 1)) / 2;
        } else {
            median = diffArray.get(diffArray.size() / 2);
        }
        return median;
    }

    public static double convertToMinutes(long time) {
        Date date = new Date(time);
        return (double) TimeUnit.MILLISECONDS.toMinutes(date.getTime());
    }


}
