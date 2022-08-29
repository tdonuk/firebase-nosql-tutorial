package com.tdonuk.passwordmanager.util;

import com.tdonuk.passwordmanager.domain.dto.UserDTO;
import com.tdonuk.passwordmanager.security.domain.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;
import java.util.Optional;

import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER;
import static com.tdonuk.passwordmanager.domain.ContextHolderParams.LOGGED_USER_USERNAME;

/** @author Taha Donuk
 * This is used to make it easier to read and write data into RequestContextHolder in session scope.
 * We are using SCOPE_SESSION by default since we need it most of the time.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SessionContext {
    public static Object getAttr(String name) {
        return RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    public static void setAttr(String name, Object value) {
        RequestContextHolder.getRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }

    public static String loggedUsername() throws Exception {
        String loggedUser = (String) getAttr(LOGGED_USER_USERNAME);

        if(StringUtils.isBlank(loggedUser)) throw new Exception("Lütfen giriş yapınız");

        return loggedUser;
    }

    public static UserDTO loggedUser() throws Exception {
        UserDTO loggedUser = (UserDTO) getAttr(LOGGED_USER);

        return loggedUser;
    }
}
