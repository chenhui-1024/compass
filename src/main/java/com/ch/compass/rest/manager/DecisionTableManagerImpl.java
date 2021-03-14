package com.ch.compass.rest.manager;

import com.ch.compass.core.model.DecisionTable;
import com.ch.compass.core.model.DecisionTableExecutionResult;
import com.ch.compass.core.repository.DecisionTableRepo;
import com.ch.compass.core.repository.Feed;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DecisionTableManagerImpl implements DecisionTableManager {
    @Autowired
    private DecisionTableRepo decisionTableRepo;


    @Override
    public Feed<DecisionTable> query(int page, int size, String sort, String order) {
        return decisionTableRepo.query(page, size, sort, order);
    }

    @Override
    public DecisionTable getDecisionTable(String decisionTableId) {
        if (StringUtils.isEmpty(decisionTableId)) {
            return null;
        }

        return decisionTableRepo.getDecisionTable(decisionTableId);
    }

    @Override
    public DecisionTable updateDecisionTable(DecisionTable decisionTable) {
        return decisionTableRepo.updateDecisionTable(decisionTable);
    }

    @Override
    public DecisionTable createDecisionTable(DecisionTable decisionTable) {
        return decisionTableRepo.createDecisionTable(decisionTable);
    }


    @Override
    public void deleteDecisionTable(String decisionTableId) {
        decisionTableRepo.deleteDecisionTable(decisionTableId);
    }

    @Override
    public DecisionTableExecutionResult execute(String decisionTableId, Map<String, Object> input, boolean force) {
        DecisionTable decisionTable = decisionTableRepo.getDecisionTable(decisionTableId);

        if(decisionTable==null){
            throw new IllegalArgumentException("决策表不存在");
        }

        return decisionTable.execute(input,force);
    }
}
