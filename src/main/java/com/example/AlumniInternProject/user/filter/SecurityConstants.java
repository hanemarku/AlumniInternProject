package com.example.AlumniInternProject.user.Filter;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Arrays;

public class SecurityConstants {
    public static final String JWT_KEY = Arrays.toString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
    public static final String JWT_HEADER = "Authorization";

}
