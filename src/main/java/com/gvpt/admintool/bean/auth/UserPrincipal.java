package com.gvpt.admintool.bean.auth;

import com.gvpt.admintool.bean.AccessRight;
import com.gvpt.admintool.bean.AccessRole;
import com.gvpt.admintool.bean.UserAdmin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final UserAdmin user;

    //

    public UserPrincipal(UserAdmin user) {
        this.user = user;
    }

    //

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        AccessRole accessRole = user.getAccessRole();
        for (AccessRight ar : accessRole.getListOfAccessRight()) {
        	authorities.add(new SimpleGrantedAuthority(ar.getAccessRightId().toString()));
		}

        return authorities;
    	
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //

    public UserAdmin getUser() {
        return user;
    }

}