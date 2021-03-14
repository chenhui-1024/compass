package com.ch.compass.rest.controller;

import com.ch.compass.core.model.Account;
import com.ch.compass.rest.manager.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private AccountManager accountManager;

    @RequestMapping(method = RequestMethod.GET)
    public List<Account> query() {
        return accountManager.query();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Account create(@RequestBody Account account) {
        return accountManager.create(account);
    }
}
