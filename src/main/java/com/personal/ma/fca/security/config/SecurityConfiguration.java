package com.personal.ma.fca.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.personal.ma.fca.security.user.Permission.ADMIN_CREATE;
import static com.personal.ma.fca.security.user.Permission.ADMIN_DELETE;
import static com.personal.ma.fca.security.user.Permission.ADMIN_READ;
import static com.personal.ma.fca.security.user.Permission.ADMIN_UPDATE;
import static com.personal.ma.fca.security.user.Permission.MANAGER_CREATE;
import static com.personal.ma.fca.security.user.Permission.MANAGER_DELETE;
import static com.personal.ma.fca.security.user.Permission.MANAGER_READ;
import static com.personal.ma.fca.security.user.Permission.MANAGER_UPDATE;
import static com.personal.ma.fca.security.user.Role.ADMIN;
import static com.personal.ma.fca.security.user.Role.MANAGER;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(opt -> opt.disable())
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(
                            "/api/v1/auth/**",
                            "/v2/api-docs",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/swagger-resources",
                            "/swagger-resources/**",
                            "/configuration/ui",
                            "/configuration/security",
                            "/swagger-ui/**",
                            "/webjars/**",
                            "/swagger-ui.html"
                    ).permitAll();

                    req.requestMatchers("/api/v1/management/**")
                            .hasAnyRole(ADMIN.name(), MANAGER.name());

                    req.requestMatchers(GET, "/api/v1/management/**")
                            .hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name());
                    req.requestMatchers(POST, "/api/v1/management/**")
                            .hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name());
                    req.requestMatchers(PUT, "/api/v1/management/**")
                            .hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name());
                    req.requestMatchers(DELETE, "/api/v1/management/**")
                            .hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name());

                    req.anyRequest().authenticated();

                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(opt -> {
                    opt.logoutUrl("/api/v1/auth/logout");
                    opt.addLogoutHandler(logoutHandler);
                    opt.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                });

        return http.build();
    }
}
