package com.tdonuk.passwordmanager.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdonuk.passwordmanager.util.JWTUtils;
import com.tdonuk.passwordmanager.util.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER_USERNAME;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final List<String> secureList = List.of(
            "/api/user",
            "/api/login",
            "/test",
            "/api/token/refresh"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(secureList.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
        } else {
            String tokenHeader = request.getHeader(AUTHORIZATION);
            if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                try {
                    JWTUtils.validate(tokenHeader);

                    SessionContext.setAttr(LOGGED_USER_USERNAME, JWTUtils.getUser(tokenHeader));

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(JWTUtils.getUser(tokenHeader),tokenHeader, JWTUtils.getAuthorities(tokenHeader));

                    SecurityContextHolder.getContext().setAuthentication(token);

                    filterChain.doFilter(request, response);
                } catch(Exception e) {
                    log.error(e.getMessage());
                    response.setHeader("Content-Type", "application/json; charset=UTF-8");
                    response.setStatus(401);
                    new ObjectMapper().writeValue(response.getOutputStream(), e.getMessage());
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
