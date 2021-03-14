package com.ch.compass.core.util;

import java.security.SecureRandom;

public class IdGenerator {

    public static String next() {
        return String.format(
                "%s%06d",
                DateUtil.now("yyyyMMddHHmmssSSS"),
                Math.abs(new SecureRandom().nextLong()) % 1000000
        );
    }
}
