package com.ch.compass.rest.mvc;

import com.ch.compass.core.repository.AccountRepo;
import com.ch.compass.core.repository.DecisionTableRepo;
import com.ch.compass.core.persistence.mapper.AccountMapper;
import com.ch.compass.core.persistence.mapper.DecisionTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    DecisionTableMapper decisionTableMapper;

    @Bean
    public DecisionTableRepo decisionTableRepo() {
        return new DecisionTableRepo(decisionTableMapper);
    }

    @Bean
    public AccountRepo accountRepo() {
        return new AccountRepo(accountMapper);
    }

}
