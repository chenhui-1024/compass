package com.ch.compass.rest.manager;

import com.ch.compass.core.model.Account;

import java.util.List;

public interface AccountManager {

    List<Account> query();

    Account update(Account account);

    Account get(String accountName);

    Account create(Account account);

    void delete(String accountName);

    void login(Account account);
}
