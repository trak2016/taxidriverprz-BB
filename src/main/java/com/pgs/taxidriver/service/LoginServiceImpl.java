package com.pgs.taxidriver.service;

import com.pgs.taxidriver.dao.UserDAO;
import com.pgs.taxidriver.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kklonowski on 2015-09-08.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDAO userDAO;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) {
        com.pgs.taxidriver.model.User user = userDAO.findByLogin(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());

        return buildUserForAuthentication(user, authorities);

    }

    // Converts User user to
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(com.pgs.taxidriver.model.User user, List<GrantedAuthority> authorities) {
        return new User(user.getLogin(), user.getPassword(), true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<UserRole> userRoles) {

        Set<SimpleGrantedAuthority> setAuths = new HashSet<>();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole().getName()));
        }

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }
}
