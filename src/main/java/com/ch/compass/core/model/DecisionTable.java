package com.ch.compass.core.model;

import com.ch.compass.core.exception.InvalidExpressionException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DecisionTable {
    private String id;
    private String name;
    private String description;
    private List<Variable> inputVariables;
    private List<Variable> outputVariables;
    private List<Rule> rules;
    private String status;
    private int version;
    private String creator;
    private String createTime;

    public DecisionTable() {
    }

    public DecisionTableExecutionResult execute(Map<String, Object> input, boolean force) {
        if (!force && !"PUBLISHED".equals(this.status)) {
            throw new IllegalArgumentException("决策表没有发布");
        }

        preCheckBeforeExecution();

        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("输入变量不能为空");
        }

        fillDefaultValueIfAbsent(input);
        checkInputDataType(input);

        DecisionTableExecutionResult result = new DecisionTableExecutionResult();
        for (Rule rule : this.rules) {
            try {
                if (rule.hit(input)) {
                    result.setRuleKey(rule.getKey());
                    result.setDecision(rule.getDecision());
                    return result;
                }
            } catch (InvalidExpressionException e) {
                throw new IllegalArgumentException(String.format("规则key=%s 表达式定义不正确", rule.getKey()));
            }
        }

        return result;
    }

    private void fillDefaultValueIfAbsent(Map<String, Object> input) {
        for (Variable v : inputVariables) {
            if (!input.containsKey(v.getName())) {
                input.put(v.getName(), DataTypes.defaultValue(v.getType()));
            }
        }
    }

    private void preCheckBeforeExecution() {
        if (CollectionUtils.isEmpty(this.inputVariables)) {
            throw new IllegalArgumentException("决策表输入变量集合为空 无法正常执行");
        }

        if (CollectionUtils.isEmpty(this.outputVariables)) {
            throw new IllegalArgumentException("决策表输出变量集合为空 无法正常执行");
        }

        if (CollectionUtils.isEmpty(this.rules)) {
            throw new IllegalArgumentException("决策表规则集合为空 无法正常执行");
        }
    }

    private void checkInputDataType(final Map<String, Object> input) {
        for (Map.Entry<String, Object> e : input.entrySet()) {
            String name = e.getKey();
            Object value = e.getValue();

            Variable variable = findInputVar(name);
            if (variable != null && value != null) {
                if (!DataTypes.matches(variable.getType(), value.getClass())) {
                    throw new IllegalArgumentException("输入数据与决策表输入变量类型不符");
                }

                if (!DataTypes.valueMatched(variable.getType(), value)) {
                    throw new IllegalArgumentException("输入数据不符合要求");
                }
            }
        }
    }

    private Variable findInputVar(String name) {
        if (this.inputVariables == null) {
            return null;
        }

        for (Variable variable : this.inputVariables) {
            if (Objects.equals(name, variable.getName())) {
                return variable;
            }
        }

        return null;
    }

    // getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Variable> getInputVariables() {
        return inputVariables;
    }

    public void setInputVariables(List<Variable> inputVariables) {
        this.inputVariables = inputVariables;
    }

    public List<Variable> getOutputVariables() {
        return outputVariables;
    }

    public void setOutputVariables(List<Variable> outputVariables) {
        this.outputVariables = outputVariables;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

