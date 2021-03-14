package com.ch.compass.core.repository;

import com.ch.compass.core.model.Account;
import com.ch.compass.core.util.IdGenerator;
import com.ch.compass.core.persistence.mapper.AccountMapper;
import com.ch.compass.core.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

public class AccountRepo {

    private AccountMapper accountMapper;

    public AccountRepo(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public Account createAccount(Account account) {
        if (StringUtils.isEmpty(account.getUsername())) {
            throw new IllegalArgumentException("用户名不能为空");
        }

        Account account1 = getAccount(account.getUsername());
        if (account1 != null) {
            throw new IllegalArgumentException("用户名已经存在");
        }

        if (StringUtils.isEmpty(account.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }

        String salt = BCrypt.gensalt();
        account.setPwdSalt(salt);
        account.setHashedPwd(BCrypt.hashpw(account.getPassword(), salt));

        account.setId(IdGenerator.next());
        account.setCreateTime(DateUtil.now("yyyy-MM-dd HH:mm:ss"));
        accountMapper.insertOne(account);
        return getAccount(account.getUsername());
    }

    public Account getAccount(String username) {
        return accountMapper.selectOne(username);
    }

    public Account updateAccount(Account account) {
        accountMapper.updateOne(account);
        return accountMapper.selectOne(account.getUsername());
    }

    public void delete(String username) {
        accountMapper.deleteOne(username);
    }

    public List<Account> query() {
        return accountMapper.selectMany();
    }
}
