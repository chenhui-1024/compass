package com.ch.compass.core.el;

import com.ch.compass.core.exception.InvalidExpressionException;
import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExprEvaluator {

    private static final List<Function> functions = new ArrayList<>();

    static {
       functions.addAll(TextFunctions.getAllFunctions());
       functions.addAll(NumberFunctions.getAllFunctions());
       functions.addAll(DateFunctions.getAllFunctions());
    }

    private static void loadFunctions(SimpleContext context) {
        for (Function function : functions) {
            context.setFunction("", function.getName(), function.getMethod());
        }
    }

    public static <T> T exec(String expression, Map<String, Object> input, Class<T> expectedType) {
        ExpressionFactory factory = new ExpressionFactoryImpl();

        SimpleContext context = new SimpleContext();

        loadFunctions(context);

        if (input != null) {
            for (Map.Entry<String, Object> entry : input.entrySet()) {
                context.setVariable(
                        entry.getKey(),
                        factory.createValueExpression(
                                entry.getValue(),
                                resolveTargetClass(entry.getValue())
                        )
                );
            }
        }

        ValueExpression ve = null;

        try {
            ve = factory.createValueExpression(context, String.format("#{%s}", expression), expectedType);
            return (T) ve.getValue(context);
        } catch (ELException e) {
            e.printStackTrace();
            throw new InvalidExpressionException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    private static Class resolveTargetClass(Object obj) {
        if (obj instanceof String) {
            return String.class;
        } else if (obj instanceof Integer || obj instanceof Long) {
            return Long.class;
        } else if (obj instanceof Float || obj instanceof Double) {
            return Double.class;
        }

        return obj.getClass();
    }
}
