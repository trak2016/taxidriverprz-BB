package com.pgs.taxidriver.init;

import com.pgs.taxidriver.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by kklonowski on 2015-09-04.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginService loginService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bootstrap/**", "/css/**", "/js/**", "/image/**", "/logout").permitAll()
                .antMatchers("/pages/admin**/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/pages/owner**/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
                .antMatchers("/pages/dispatcher**/**").access(
                "hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER') or hasRole('ROLE_DISPATCHER')")
                .antMatchers("/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER') or hasRole('ROLE_DISPATCHER') or hasRole('ROLE_DRIVER')")
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .formLogin()
                .loginPage("/login.xhtml")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .defaultSuccessUrl("/pages/index.xhtml", true)
                .and()
                .exceptionHandling().accessDeniedPage("/403.xhtml")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .permitAll();
        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService);

    }
}
