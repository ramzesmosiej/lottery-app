package com.ramzescode.socials.config;

import com.ramzescode.socials.config.jwt.JwtAuthenticationProvider;
import com.ramzescode.socials.config.jwt.JwtTokenFilter;
import com.ramzescode.socials.repository.UserRepository;
import com.ramzescode.socials.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private final UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(UserRepository userRepository, UserDetailsServiceImpl userDetailsService, JwtAuthenticationProvider jwtAuthenticationProvider, JwtTokenFilter jwtTokenFilter) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.jwtTokenFilter = jwtTokenFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/auth/login", "/auth/signup", "/auth/ping").permitAll()
                .antMatchers("/ping/admin").hasRole("ADMIN")
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.eraseCredentials(false)
                .authenticationProvider(jwtAuthenticationProvider);
        return builder.build();
    }




}
