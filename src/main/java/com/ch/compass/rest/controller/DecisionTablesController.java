package com.ch.compass.rest.controller;

import com.ch.compass.core.model.DecisionTable;
import com.ch.compass.core.repository.Feed;
import com.ch.compass.rest.manager.DecisionTableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/decision-tables")
public class DecisionTablesController {

    @Autowired
    private DecisionTableManager decisionTableManager;


    @RequestMapping(method = RequestMethod.GET)
    public Feed<DecisionTable> get(
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "size",defaultValue = "10")int size,
            @RequestParam(value = "orderBy",defaultValue = "createTime")String orderBy,
            @RequestParam(value = "order",defaultValue = "asc")String order
    ) {
        return decisionTableManager.query(page,size,orderBy,order);
    }

    @RequestMapping(method = RequestMethod.POST)
    public DecisionTable createDecisionTable(@RequestBody DecisionTable decisionTableModel) {
        return decisionTableManager.createDecisionTable(decisionTableModel);
    }

}
