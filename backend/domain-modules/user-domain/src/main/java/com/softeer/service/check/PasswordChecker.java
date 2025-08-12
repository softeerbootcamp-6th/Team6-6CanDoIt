package com.softeer.service.check;

import com.softeer.error.ExceptionCreator;
import com.softeer.exception.UserException;
import com.softeer.service.Check;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PasswordChecker implements Check {
    
    private static final int LOWER_BOUND = 8;

    @Override
    public boolean check(String password) {
        if (!StringUtils.hasText(password) || !satisfyMinimumLength(password)) throw ExceptionCreator.create(UserException.PASSWORD_OVER_8);
        if(!isConsistedOfEnglishAndDigit(password)) throw ExceptionCreator.create(UserException.PASSWORD_ONLY_ENGLISH_AND_DIGIT, "password :" + password);

        return true;
    }

    private boolean isConsistedOfEnglishAndDigit(String password) {
        boolean hasEnglish = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    hasEnglish = true;
                } else {
                    return false;
                }
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                return false;
            }

            if (hasEnglish && hasDigit) {
                return true;
            }
        }

        return false;
    }

    private boolean satisfyMinimumLength(String password) {
        return password.length() >= LOWER_BOUND;
    }
}