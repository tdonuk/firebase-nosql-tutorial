package com.tdonuk.passwordmanager.security;

import com.tdonuk.passwordmanager.security.domain.CustomUserDetailsService;
import com.tdonuk.passwordmanager.security.filter.CustomAuthenticationFilter;
import com.tdonuk.passwordmanager.security.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .and()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/user", "/api/login", "/test", "/api/user/me").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(authenticationFilter())
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter(authenticationManagerBean());

        authFilter.setFilterProcessesUrl("/api/login");
        authFilter.setPostOnly(true);
        authFilter.setUsernameParameter("username");
        authFilter.setAllowSessionCreation(false);

        return authFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return this.userDetailsService;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("**"));
        corsConfiguration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        corsConfiguration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        corsConfiguration.setExposedHeaders(Arrays.asList(CorsConfiguration.ALL));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
