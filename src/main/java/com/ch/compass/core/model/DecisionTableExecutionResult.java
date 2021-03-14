package com.ch.compass.core.model;

import java.util.Map;

public class DecisionTableExecutionResult {
    private String ruleKey;
    private Map<String,Object> decision;

    public String getRuleKey() {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

    public Map<String, Object> getDecision() {
        return decision;
    }

    public void setDecision(Map<String, Object> decision) {
        this.decision = decision;
    }
}
