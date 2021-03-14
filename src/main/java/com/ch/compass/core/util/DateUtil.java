package com.ch.compass.core.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class DateUtil {


    private DateUtil() {
    }

    public static String now(String pattern) {
        return FastDateFormat.getInstance(pattern).format(new Date());
    }
}
