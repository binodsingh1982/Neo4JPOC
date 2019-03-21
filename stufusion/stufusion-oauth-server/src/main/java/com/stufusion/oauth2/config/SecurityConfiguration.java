package com.stufusion.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stufusion.oauth2.service.UserService;

@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off

        http
        .authorizeRequests()
            .antMatchers("/css/**", "/index").permitAll()
            .antMatchers("/images/**", "/index").permitAll()
            .antMatchers("/oauth/check_token").permitAll()
            .antMatchers("/oauth/confirm_access").permitAll()
            .antMatchers("/oauth/error").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/api/provider/linkedin").permitAll()
            .antMatchers("/api/provider/linkedin/token").permitAll()
            .antMatchers("/api/provider/linkedin/callback").permitAll()
            .antMatchers("/api/**").permitAll()
            .anyRequest()
                .authenticated().and().httpBasic().and().csrf().disable()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error");
        
        // @formatter:on

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

}
