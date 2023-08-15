package com.example.AlumniInternProject.enumerations;

import com.example.AlumniInternProject.entity.Authority;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.*;

import static com.example.AlumniInternProject.user.filter.Authority.ADMIN_AUTHORITIES;
import static com.example.AlumniInternProject.user.filter.Authority.USER_AUTHORITIES;
@Getter
public enum Role {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES);

    private List<String> authorities;

    private static final Map<String, Authority> authorityMap = new HashMap<>();

    static {
        for (Role role : values()) {
            role.getAuthorities().forEach(authority -> authorityMap.put(authority, new Authority(authority)));
        }
    }

    Role(List<String> authorities) {
        this.authorities = authorities;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public static Set<Authority> getAllAuthorities() {
        return new HashSet<>(authorityMap.values());
    }

    public static Authority getAuthorityByName(String name) {
        return authorityMap.get(name);
    }
}
