package com.demo.bookstore.security;

import com.demo.bookstore.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {
    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
