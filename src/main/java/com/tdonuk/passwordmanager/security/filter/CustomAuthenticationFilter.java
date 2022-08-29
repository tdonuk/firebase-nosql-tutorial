package com.tdonuk.passwordmanager.security.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdonuk.passwordmanager.security.domain.CustomUserDetails;
import com.tdonuk.passwordmanager.util.JWTUtils;
import com.tdonuk.passwordmanager.util.SessionContext;
import com.tdonuk.passwordmanager.util.TimeConstants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER;
import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER_USERNAME;

@NoArgsConstructor
@AllArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info(String.format("authentication attempt from [%s]", request.getRemoteAddr()));

        if("POST".equalsIgnoreCase(request.getMethod())) {
            String username, password;

            try {
                String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

                JsonNode json = new ObjectMapper().readTree(body);

                username = json.get("username").asText();
                password = json.get("password").asText();

                logger.info(String.format("authenticating [%s] as [%s]...", request.getRemoteAddr(), username));

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

                return this.authenticationManager.authenticate(token);
            } catch (IOException e) {
                logger.info(String.format("authentication failed for [%s]. reason: ", e.getMessage()));
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info(String.format("Authentication success: [%s]", request.getRemoteAddr()));

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

        if(Objects.isNull(userDetails)) {
            response.setStatus(400);
            new ObjectMapper().writeValue(response.getOutputStream(), "An unknown error is happened while logging in.. please re-login in order to continue");
            return;
        };

        logger.info(String.format("creating tokens for authenticated user[%s]...", userDetails.getUsername()));

        String accessToken = JWTUtils.createDefault(userDetails.getUsername(), List.of("APP_USER"));
        String refreshToken = JWTUtils.create(userDetails.getUsername(), TimeConstants.months(12), Algorithm.HMAC256("secret".getBytes()), List.of());

        logger.info(String.format("access and refresh tokens created for authenticated user[%s], preparing response..", userDetails.getUsername()));

        response.setHeader("accessToken", accessToken);
        response.setHeader("refreshToken", refreshToken);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");

        SessionContext.setAttr(LOGGED_USER, userDetails.getUser());
        SessionContext.setAttr(LOGGED_USER_USERNAME, userDetails.getUsername());

        userDetails.getUser().setPassword("[PROTECTED]");
        userDetails.getUser().setLastLogin(new Date());

        new ObjectMapper().writeValue(response.getOutputStream(), userDetails.getUser());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.info(String.format("unsuccessful authentication attempt from [%s]", request.getRemoteAddr()));

        response.setStatus(401);
        response.addHeader("Content-Type", "application/json; charset=UTF-8");

        new ObjectMapper().writeValue(response.getOutputStream(), "Invalid username or password");
    }
}
