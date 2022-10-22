package com.ramzescode.socials.jwt;


import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ramzescode.socials.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


@Service
public class JwtUtil {

    private final String jwtSecret = "CctlD5JL16m8wLTgsFNhzqjQP";


    public String generateAccessToken(String username) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret.getBytes());


        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .sign(algorithm);
    }

    public boolean validate(String token) {
        try {
            long expiresAt = JWT.decode(token).getExpiresAt().getTime();
            Calendar cal = Calendar.getInstance();
            if (expiresAt >= cal.getTime().getTime()) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.printf("JWT is invalid - {%s}%n", e.getMessage());
        }

        return false;
    }

    public String getUserName(String token) {
        return JWT.decode(token).getSubject().trim();
    }


}
