package com.ch.compass.rest.manager;

import com.ch.compass.core.model.DecisionTable;
import com.ch.compass.core.model.DecisionTableExecutionResult;
import com.ch.compass.core.repository.Feed;

import java.util.Map;

public interface DecisionTableManager {
    Feed<DecisionTable> query(int page, int size, String sort, String order);

    DecisionTable getDecisionTable(String decisionTableId);

    DecisionTable updateDecisionTable(DecisionTable decisionTableModel);

    DecisionTable createDecisionTable(DecisionTable decisionTableModel);

    void deleteDecisionTable(String decisionTableId);

    DecisionTableExecutionResult execute(String decisionTableId, Map<String,Object> input, boolean force);
}
