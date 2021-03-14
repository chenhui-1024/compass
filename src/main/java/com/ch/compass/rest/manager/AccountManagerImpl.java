package com.ch.compass.rest.manager;

import com.ch.compass.core.model.Account;
import com.ch.compass.core.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class AccountManagerImpl implements AccountManager {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public List<Account> query() {
        return accountRepo.query();
    }

    @Override
    public Account update(Account account) {
        Account account2 = accountRepo.updateAccount(account);
        hide(account2);
        return account2;
    }

    @Override
    public Account get(String accountName) {
        if (StringUtils.isEmpty(accountName)) {
            return null;
        }

        Account account = accountRepo.getAccount(accountName);
        hide(account);

        return account;
    }

    @Override
    public Account create(Account account) {
       accountRepo.createAccount(account);
       return accountRepo.getAccount(account.getUsername());
    }

    @Override
    public void delete(String accountName) {
        if (StringUtils.isEmpty(accountName)) {
            return;
        }

        accountRepo.delete(accountName);
    }

    @Override
    public void login(Account account) {
        if (StringUtils.isEmpty(account.getUsername()) || StringUtils.isEmpty(account.getPassword())) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }

        Account account1 = accountRepo.getAccount(account.getUsername());
        if (account1 == null) {
            throw new IllegalArgumentException("用户名不存在");
        }

        String inputPassword = account.getPassword();
        String hashedPwd = account1.getHashedPwd();

        if (!BCrypt.hashpw(inputPassword, account1.getPwdSalt()).equals(hashedPwd)) {
            throw new IllegalArgumentException("用户名或密码填写错误");
        }
    }

    private void hide(Account account) {
        if (account != null) {
            account.setPwdSalt(null);
            account.setHashedPwd(null);
            account.setPassword(null);
        }
    }
}
