package com.eagledev.todoapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AuthenticationProvider authentication;
    private final JwtAuthenticationFilter filter;

    public SecurityConfig(AuthenticationProvider authenticationProvider , JwtAuthenticationFilter jwtAuthenticationFilter){
        this.authentication =authenticationProvider;
        this.filter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(config ->
                        config.requestMatchers("api/auth/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET , "api/users/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                        )
                .sessionManagement(sessionConf -> sessionConf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authentication)
                .addFilterBefore(filter , UsernamePasswordAuthenticationFilter.class)
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/api/logout")
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .logoutSuccessHandler(((request, response, authentication1) -> SecurityContextHolder.clearContext())));

        return security.build();
    }
}
