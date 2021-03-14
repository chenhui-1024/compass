package com.ch.compass.core.el;

import org.apache.commons.lang3.time.DateUtils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DateFunctions {

    private static Map<String, Method> methodMap = new HashMap<>();

    static {
        try {
            methodMap.put("daysDiff", DateFunctions.class.getDeclaredMethod("daysDiff", String.class, String.class));
            methodMap.put("year", DateFunctions.class.getDeclaredMethod("year", String.class));
            methodMap.put("month", DateFunctions.class.getDeclaredMethod("month", String.class));
            methodMap.put("dayOfMonth", DateFunctions.class.getDeclaredMethod("dayOfMonth", String.class));
        } catch (NoSuchMethodException e) {
            // ignore
        }

    }

    private DateFunctions() {
    }

    public static List<Function> getAllFunctions() {
        List<Function> functions = new ArrayList<>();

        for (final Map.Entry<String, Method> entry : methodMap.entrySet()) {
            functions.add(
                    new Function() {
                        @Override
                        public String getName() {
                            return entry.getKey();
                        }

                        @Override
                        public Method getMethod() {
                            return entry.getValue();
                        }
                    }
            );
        }

        return functions;
    }

    public static long daysDiff(String date1, String date2) {
        try {
            Date d1 = DateUtils.parseDate(date1, "yyyy-MM-dd");
            Date d2 = DateUtils.parseDate(date2, "yyyy-MM-dd");

            return TimeUnit.DAYS.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            return Long.MAX_VALUE;
        }
    }

    public static long year(String date) {
        return Long.parseLong(date.substring(0, 4));
    }

    public static int month(String date) {
        return Integer.parseInt(date.substring(5, 7));
    }

    public static int dayOfMonth(String date) {
        return Integer.parseInt(date.substring(8, 10));
    }
}
