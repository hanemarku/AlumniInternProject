package com.example.AlumniInternProject.user.filter;

import java.util.ArrayList;
import java.util.List;

public class Authority {
    public static final List<String> USER_AUTHORITIES = new ArrayList<>(List.of("user:read"));
    public static final List<String> ADMIN_AUTHORITIES = new ArrayList<>(List.of("user:read", "user:create", "user:update", "user:delete"));
}
