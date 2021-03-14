package com.ch.compass.rest.controller;

import com.ch.compass.core.model.DecisionTable;
import com.ch.compass.rest.manager.DecisionTableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decision-tables/{decisionTableId}")
public class DecisionTableController {

    @Autowired
    private DecisionTableManager decisionTableManager;

    @RequestMapping(method = RequestMethod.GET)
    public DecisionTable get(@PathVariable("decisionTableId") String decisionTableId) {
        return decisionTableManager.getDecisionTable(decisionTableId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public DecisionTable update(
            @PathVariable("decisionTableId") String decisionTableId,
            @RequestBody DecisionTable decisionTable
    ) {
        decisionTable.setId(decisionTableId);
        return decisionTableManager.updateDecisionTable(decisionTable);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("decisionTableId") String decisionTableId) {
        decisionTableManager.deleteDecisionTable(decisionTableId);
    }

}
