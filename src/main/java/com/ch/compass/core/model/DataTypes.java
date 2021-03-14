package com.ch.compass.core.model;

import com.ch.compass.core.util.DateUtil;

import java.util.Arrays;
import java.util.List;

public class DataTypes {

    private static final List<String> SUPPORTED_TYPES = Arrays.asList(
            "NUMBER",
            "TEXT",
            "BOOL",
            "DATE"
    );

    public static Object defaultValue(String type) {
        switch (type) {
            case "NUMBER":
                return new Long(0);
            case "TEXT":
                return "";
            case "BOOL":
                return Boolean.FALSE;
            case "DATE":
                return DateUtil.now("yyyy-MM-dd");
            default:
                throw new IllegalArgumentException();
        }
    }

    public static boolean support(String type) {
        return SUPPORTED_TYPES.contains(type);
    }

    public static boolean matches(String type, Class clz) {
        switch (type) {
            case "NUMBER":
                return Number.class.isAssignableFrom(clz);
            case "TEXT":
            case "DATE":
                return String.class.isAssignableFrom(clz);
            case "BOOL":
                return Boolean.class.isAssignableFrom(clz);
            default:
                return false;
        }
    }

    public static boolean valueMatched(String type, Object value) {
        switch (type) {
            case "DATE":
                return checkDateFormat(String.valueOf(value));
            default:
                return true;
        }
    }

    private static boolean checkDateFormat(String date) {
        if (date.length() != 10) {
            return false;
        }

        if (date.split("-").length != 3) {
            return false;
        }

        return true;
    }
}
