package com.ch.compass.core.el;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NumberFunctions {

    private static Map<String, Method> methodMap = new HashMap<>();

    static {
        try {
            methodMap.put("abs", NumberFunctions.class.getDeclaredMethod("abs", Number.class));
            methodMap.put("sqrt", NumberFunctions.class.getDeclaredMethod("sqrt", Number.class));
            methodMap.put("pow", NumberFunctions.class.getDeclaredMethod("pow", Number.class,Number.class));
        } catch (NoSuchMethodException e) {
        }
    }


    private NumberFunctions() {
    }

    public static List<Function> getAllFunctions(){
        List<Function> functions=new ArrayList<>();

        for(final Map.Entry<String,Method> entry:methodMap.entrySet()){
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

    public static Number abs(Number a) {
        if (a instanceof Integer) {
            return abs(a.intValue());
        } else if (a instanceof Long) {
            return abs(a.longValue());
        } else if (a instanceof Float) {
            return abs(a.floatValue());
        } else if (a instanceof Double) {
            return abs(a.doubleValue());
        }

        throw new RuntimeException();
    }

    public static int abs(Integer a) {
        return Math.abs(a);
    }

    public static long abs(Long a) {
        return Math.abs(a);
    }

    public static float abs(Float a) {
        return Math.abs(a);
    }

    public static double abs(Double a) {
        return Math.abs(a);
    }

    public static Number sqrt(Number a) {
        if(a instanceof Integer){
            return sqrt(a.intValue());
        }else if(a instanceof Long){
            return sqrt(a.longValue());
        }else if(a instanceof Float){
            return sqrt(a.floatValue());
        }else if(a instanceof Double){
            return sqrt(a.doubleValue());
        }

        throw new RuntimeException();
    }

    public static double sqrt(Integer a) {
        return Math.sqrt(a);
    }

    public static double sqrt(Long a) {
        return Math.sqrt(a);
    }

    public static double sqrt(Float a) {
        return Math.sqrt(a);
    }

    public static double sqrt(Double a) {
        return Math.sqrt(a);
    }

    public static Number pow(Number a, Number b) {
        return Math.pow(a.doubleValue(),b.doubleValue());
    }
}
