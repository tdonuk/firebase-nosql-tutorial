package com.tdonuk.passwordmanager.util;

import com.tdonuk.passwordmanager.domain.Name;
import com.tdonuk.passwordmanager.domain.dto.UserDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtils {
    public static final String SPECIAL_CHARS = "çşüğıöç\"ÇŞÜIĞÖÇ \n";
    public static final String ALPHABET = "";

    static {
        StringBuilder sb = new StringBuilder(ALPHABET);
        for(char c = 'A' ; c < 'z'; c++) {
            sb.append(c);
        }
    }

    public static void validateUser(UserDTO user) throws Exception {
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validateName(user.getName());
    }

    public static void validateFields(Map<String, Object> fields) throws Exception {
        if(fields.keySet().stream().anyMatch(key -> key.equals("lastLogin"))) throw new Exception("Last login date can not be updated manually");

        for(Map.Entry<String, Object> entry : fields.entrySet()) {
            switch (entry.getKey()) {
                case "password":
                    validatePassword(entry.getValue().toString());
                    break;
                case "name":
                    validateName((Name) entry.getValue());
                    break;
                case "email":
                    validateEmail(entry.getValue().toString());
                    break;
                case "username":
                    validateUsername(entry.getValue().toString());
                    break;
            }
        }
    }

    public static void validateEmail(String email) throws Exception{
        if(isLengthBetween(email, 9, 30)) {
            Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n)*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);

            if(pattern.matcher(email).matches()) {
                if(!containsSpecialCharacters(email)) {
                    return;
                }
                else throw new Exception("Email should not contain special characters");
            }
            else throw new Exception("Email is not valid");
        }
        else throw new Exception("Email should have length of 9-30 characters");
    }

    public static void validateUsername(String username) throws Exception {
        if(isLengthBetween(username, 4, 20)) {
            if(!containsSpecialCharacters(username)) {
                return;
            }
            else throw new Exception("Username should not contain special characters");
        }
        else throw new Exception("Username should have a length of 4-20 characters");
    }

    public static void validateName(Name name) throws Exception {
        String firstname = name.getFirstname(), lastname = name.getLastname();
        if(StringUtils.isNotBlank(firstname) && StringUtils.isNotBlank(lastname)) {
            if(isLengthBetween(firstname, 2, 12) && isLengthBetween(lastname, 2, 12)) {
                return;
            }
            else throw new Exception("Firstname and lastname both should have a length of 2-12 characters");
        }
        else throw new Exception("Firstname and lastname should be valid");
    }

    public static void validatePassword(String password) throws Exception {
        if(isLengthBetween(password, 8, 30)) {
            if(!StringUtils.containsWhitespace(password)) {
                if(!containsSpecialCharacters(password)) {
                    return;
                }
                throw new Exception("Password should not contain special characters");
            }
            else throw new Exception("Password should not contain whitespace character");
        }
        else throw new Exception("Password should have a length of 8-30 characters");
    }

    public static boolean containsSpecialCharacters(String text) {
        if(StringUtils.containsAny(text, SPECIAL_CHARS.toCharArray())) return true;
        else return false;
    }

    public static boolean isLengthBetween(String text, int min, int max) {
        return ((text.length() >= min) && (text.length() <= max));
    }
}
