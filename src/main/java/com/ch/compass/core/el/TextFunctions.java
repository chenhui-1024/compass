package com.ch.compass.core.el;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TextFunctions {
    private static Map<String, Method> methodMap = new HashMap<>();

    static {
        try {
            methodMap.put("length", TextFunctions.class.getDeclaredMethod("length", String.class));
            methodMap.put("startWith", TextFunctions.class.getDeclaredMethod("startWith", String.class, String.class));
            methodMap.put("endWith", TextFunctions.class.getDeclaredMethod("endsWith", String.class, String.class));
            methodMap.put("contain", TextFunctions.class.getDeclaredMethod("contains", String.class, String.class));
        } catch (NoSuchMethodException e) {
        }
    }


    private TextFunctions() {
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

    public static Function getFunction(final String name) {
        return new Function() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public Method getMethod() {
                return methodMap.get(name);
            }
        };
    }


    public static int length(String str) {
        if (str == null) {
            return 0;
        }

        return str.length();
    }

    public static boolean startWith(String str, String prefix) {
        return str.startsWith(prefix);
    }

    public static boolean endsWith(String str, String suffix) {
        return str.endsWith(suffix);
    }

    public static boolean contains(String str, String s) {
        return str.contains(s);
    }
}
