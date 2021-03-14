package com.ch.compass.rest.controller;

import com.ch.compass.core.model.DecisionTableExecutionResult;
import com.ch.compass.rest.manager.DecisionTableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/decision-tables/{decisionTableId}/execution")
public class DecisionTableExecutionController {

    @Autowired
    private DecisionTableManager decisionTableManager;

    @RequestMapping(method = RequestMethod.POST)
    public DecisionTableExecutionResult acquireOutput(
            @PathVariable("decisionTableId") String decisionTableId,
            @RequestParam(value = "force")boolean force,
            @RequestBody Map<String, Object> input
    ) {
        return decisionTableManager.execute(decisionTableId, input,force);
    }

}
