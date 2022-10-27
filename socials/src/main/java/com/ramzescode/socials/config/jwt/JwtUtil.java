package com.ramzescode.socials.config.jwt;


import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ramzescode.socials.service.AuthoritiesService;
import org.springframework.stereotype.Service;


@Service
public class JwtUtil {

    private final String jwtSecret = "CctlD5JL16m8wLTgsFNhzqjQP";

    private final AuthoritiesService authoritiesService;

    public JwtUtil(AuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }


    public String generateAccessToken(String username) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret.getBytes());


        return JWT.create()
                .withSubject(username)
                .withClaim("roles", authoritiesService.getUserAuthorities(username))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
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
