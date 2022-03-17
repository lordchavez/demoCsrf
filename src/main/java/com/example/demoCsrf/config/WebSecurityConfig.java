package com.example.demoCsrf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http
                .csrf().disable();
        http
                .cors().disable();
        http
                .authorizeRequests()
                .antMatchers( "/", "/login" ).permitAll()
                .antMatchers( "/meun" ).authenticated()
                .and()
                .logout()
                .logoutSuccessUrl( "/login" )
                .deleteCookies("JSESSIONID");
    }

}
