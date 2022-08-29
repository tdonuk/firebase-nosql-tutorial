package com.tdonuk.passwordmanager.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

/** @author Taha Donuk
 * This is used to validate, create and decode JWT Bearer tokens.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JWTUtils {
    private static DecodedJWT decodedJWT;

    public static String getUser(String tokenHeader) throws Exception {
        if(Objects.isNull(decodedJWT)) {
            validate(tokenHeader);
        }
        return decodedJWT.getSubject();
    }

    public static List<SimpleGrantedAuthority> getAuthorities(String tokenHeader) throws Exception {
        if(Objects.isNull(decodedJWT)) {
            validate(tokenHeader);
        }
        return decodedJWT.getClaim("authorities").asList(SimpleGrantedAuthority.class);
    }

    public static void validate(String tokenHeader) throws Exception{
        try {
            decode(tokenHeader);
        } catch(TokenExpiredException e) {
            log.info("An error has occurred while authenticating the user.");
            throw new TokenExpiredException("Maximum session time is expired.", decodedJWT.getExpiresAtAsInstant());
        } catch(JWTDecodeException e) {
            throw new JWTDecodeException("An unknown error has happened: " + e. getMessage());
        } catch(Exception e) {
            throw e;
        }

    }

    private static DecodedJWT decode(String tokenHeader) {
        String token = tokenHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();

        decodedJWT = verifier.verify(token);

        return decodedJWT;
    }

    public static String create(String subject, long expiresIn, Algorithm alg, List<String> authorities) {
        String token = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresIn))
                .withClaim("authorities", authorities)
                .sign(alg);

        return token;
    }

    public static String createDefault(String subject, List<String> authorities) {
        return create(subject, TimeConstants.hours(1), Algorithm.HMAC256("secret"), authorities);
    }
}