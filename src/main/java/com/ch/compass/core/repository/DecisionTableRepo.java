package com.ch.compass.core.repository;

import com.ch.compass.core.model.DataTypes;
import com.ch.compass.core.model.DecisionTable;
import com.ch.compass.core.model.Rule;
import com.ch.compass.core.model.Variable;
import com.ch.compass.core.util.IdGenerator;
import com.ch.compass.core.persistence.mapper.DecisionTableMapper;
import com.ch.compass.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DecisionTableRepo {

    private static final Map<String, String> PROPERTIES_MAP = new HashMap<>();

    static {
        PROPERTIES_MAP.put("id", "id");
        PROPERTIES_MAP.put("name", "name");
        PROPERTIES_MAP.put("creator", "creator");
        PROPERTIES_MAP.put("createTime", "create_time");
    }

    private DecisionTableMapper decisionTableMapper;

    public DecisionTableRepo(DecisionTableMapper decisionTableMapper) {
        this.decisionTableMapper = decisionTableMapper;
    }

    public DecisionTable createDecisionTable(DecisionTable decisionTable) {
        preCheck(decisionTable);

        String decisionTableId = IdGenerator.next();
        decisionTable.setId(decisionTableId);
        decisionTable.setStatus("DRAFT");
        decisionTable.setVersion(0);
        decisionTable.setCreator(SecurityContextHolder.getContext().getAuthentication().getName());
        decisionTable.setCreateTime(DateUtil.now("yyyy-MM-dd HH:dd:ss"));

        decisionTableMapper.insertOne(decisionTable);

        return decisionTableMapper.selectOne(decisionTable.getId());
    }

    private void preCheck(DecisionTable decisionTable) {
        if (decisionTable.getName() == null || decisionTable.getName().length() == 0) {
            throw new IllegalArgumentException("名称不能为空");
        }

        if (StringUtils.isEmpty(decisionTable.getName())) {
            throw new IllegalArgumentException("名称不能为空");
        }

        if (StringUtils.isEmpty(decisionTable.getDescription())) {
            throw new IllegalArgumentException("说明不能为空");
        }

        List<Variable> outputVariables = decisionTable.getOutputVariables();
        checkVariables(outputVariables);
        List<Variable> inputVariables = decisionTable.getInputVariables();
        checkVariables(inputVariables);

        List<Rule> rules = decisionTable.getRules();
        checkRules(rules);
    }

    private void checkVariables(List<Variable> variables) {
        if (variables == null) {
            return;
        }

        Set<String> names = new HashSet<>();

        for (Variable variable : variables) {
            checkVariable(variable);
            if (names.contains(variable.getName())) {
                throw new IllegalArgumentException("变量名称不能重复");
            }
            names.add(variable.getName());
        }
    }

    private void checkVariable(Variable variable) {
        if (StringUtils.isEmpty(variable.getName())) {
            throw new IllegalArgumentException("变量名称不能为空");
        }

        if (StringUtils.isEmpty(variable.getType())) {
            throw new IllegalArgumentException("变量类型不能为空");
        }

        if (!DataTypes.support(variable.getType())) {
            throw new IllegalArgumentException("变量类型不支持");
        }
    }

    private void checkRules(List<Rule> rules) {
        if (rules == null) {
            return;
        }

        Set<String> keys = new HashSet<>();

        for (Rule rule : rules) {
            checkRule(rule);
            if (keys.contains(rule.getKey())) {
                throw new IllegalArgumentException("规则key不能重复");
            }
            keys.add(rule.getKey());
        }
    }

    private void checkRule(Rule rule) {
        if (StringUtils.isEmpty(rule.getKey())) {
            throw new IllegalArgumentException("规则Key不能为空");
        }

        if (StringUtils.isEmpty(rule.getExpression())) {
            throw new IllegalArgumentException("规则表达式不能为空");
        }

        if (rule.getDecision() == null || rule.getDecision().isEmpty()) {
            throw new IllegalArgumentException("规则决策需要设置");
        }
    }

    public DecisionTable getDecisionTable(String id) {
        return decisionTableMapper.selectOne(id);
    }

    public DecisionTable updateDecisionTable(DecisionTable decisionTable) {
        DecisionTable current = getDecisionTable(decisionTable.getId());

        if (current == null) {
            throw new IllegalArgumentException("决策表不存在");
        }

        if (decisionTable.getVersion() != current.getVersion()) {
            throw new IllegalArgumentException("决策表已经被更新过，请刷新后再更新");
        }

        if (decisionTable.getName() != null && decisionTable.getName().length() == 0) {
            throw new IllegalArgumentException("决策表名称不能为空");
        }

        checkVariables(decisionTable.getInputVariables());
        checkVariables(decisionTable.getOutputVariables());
        checkRules(decisionTable.getRules());

        decisionTable.setVersion(decisionTable.getVersion() + 1);

        decisionTableMapper.updateOne(decisionTable);

        return getDecisionTable(decisionTable.getId());
    }

    public void deleteDecisionTable(String decisionTableId) {
        decisionTableMapper.deleteOne(decisionTableId);
    }


    public Feed<DecisionTable> query(int page, int size, String orderBy, String order) {
        checkPaginationParams(page, size, orderBy, order);

        Feed<DecisionTable> currentFeed = new Feed<>();

        List<DecisionTable> decisionTables = decisionTableMapper.selectMany(page * size, size, "create_time", order);
        currentFeed.setData(decisionTables);
        int total = decisionTableMapper.count();
        currentFeed.setTotal(total);
        currentFeed.setPage(page);
        currentFeed.setSize(size);
        return currentFeed;
    }

    private void checkPaginationParams(int page, int size, String orderBy, String order) {
        if (page < 0 || size < 0) {
            throw new IllegalArgumentException("参数page或size不能小于0");
        }

        if (StringUtils.isEmpty(orderBy) || StringUtils.isEmpty(order)) {
            throw new IllegalArgumentException("参数orderBy或order不能为空");
        }

        if (!PROPERTIES_MAP.keySet().contains(orderBy)) {
            throw new IllegalArgumentException(String.format("%s不支持排序", orderBy));
        }
    }
}
