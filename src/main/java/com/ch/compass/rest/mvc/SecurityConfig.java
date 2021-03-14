package com.ch.compass.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  AuthenticationFilter authenticationFilter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class).csrf().disable();
    }
}
