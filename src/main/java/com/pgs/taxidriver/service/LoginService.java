package com.pgs.taxidriver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by kklonowski on 2015-09-08.
 */
public interface LoginService extends UserDetailsService {

    /**
     * @param login
     * @return
     */
    UserDetails loadUserByUsername(final String login);
}
