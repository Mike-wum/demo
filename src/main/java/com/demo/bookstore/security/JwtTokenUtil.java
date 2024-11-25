package com.demo.bookstore.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("ALL")
@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = 4874575994108308290L;

    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_PERMISSION = "permission";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    @Deprecated
    public Collection<GrantedAuthority> getAuthoritiesFromToken(String token) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        try {
            final Claims claims = getClaimsFromToken(token);
            List permissions = claims.get("permission", List.class);
            for (Object o : permissions) {
                authorities.add(new SimpleGrantedAuthority(o.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorities;
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration != null) {
            return expiration.before(new Date());
        } else {
            return true;
        }
    }

    public String generateToken(UserDetails userDetails) {
        Date current = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
//        claims.put(CLAIM_KEY_PERMISSION, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        claims.put(CLAIM_KEY_PERMISSION,"no-use");

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setNotBefore(current)
                .setIssuedAt(current)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        return getUsernameFromToken(token) != null && !isTokenExpired(token);
    }
}
