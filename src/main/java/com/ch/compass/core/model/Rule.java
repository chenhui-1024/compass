package com.ch.compass.core.model;

import com.ch.compass.core.el.ExprEvaluator;

import java.util.Map;

public class Rule {
    private String key;
    private String expression;
    private Map<String, Object> decision;

    public boolean hit(Map<String, Object> input) {
        return ExprEvaluator.exec(expression,input,boolean.class);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Map<String, Object> getDecision() {
        return decision;
    }

    public void setDecision(Map<String, Object> decision) {
        this.decision = decision;
    }
}