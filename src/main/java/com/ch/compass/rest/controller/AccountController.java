package com.ch.compass.rest.controller;

import com.ch.compass.core.model.Account;
import com.ch.compass.rest.manager.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts/{accountName}")
public class AccountController {

    @Autowired
    private AccountManager accountManager;

    @RequestMapping(method = RequestMethod.GET)
    public Account get(@PathVariable("accountName") String accountName) {
        return accountManager.get(accountName);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Account update(@PathVariable("accountName") String accountName, @RequestBody Account account) {
        if (account.getUsername() != null && !account.getUsername().equals(accountName)) {
            throw new IllegalArgumentException();
        }

        account.setUsername(accountName);

        return accountManager.update(account);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@PathVariable("accountName") String accountName) {
        accountManager.delete(accountName);
    }
}
