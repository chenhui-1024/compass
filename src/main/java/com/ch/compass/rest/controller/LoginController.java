package com.ch.compass.rest.controller;

import com.ch.compass.core.model.Account;
import com.ch.compass.rest.manager.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    AccountManager accountManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody Account account) {
        accountManager.login(account);
    }
}
