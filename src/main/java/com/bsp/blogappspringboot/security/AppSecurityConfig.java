package com.bsp.blogappspringboot.security;

import com.bsp.blogappspringboot.users.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfig {

    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTService jwtService;
    private UsersService usersService;



    public AppSecurityConfig(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
        this.jwtAuthenticationFilter = new JWTAuthenticationFilter(new JWTAuthenticationManager(jwtService, usersService));
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users", "/users/*").permitAll()
                .antMatchers(HttpMethod.GET, "/articles", "/articles/*", "articles/user/*").permitAll()
                .antMatchers(HttpMethod.POST, "/articles", "/articles/*").permitAll()
                .antMatchers(HttpMethod.PATCH, "/articles", "/articles/update/*").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/", "/h2-console/*");
    }
}
