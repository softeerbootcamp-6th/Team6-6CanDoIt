package com.softeer.service.check;

import com.softeer.error.ExceptionCreator;
import com.softeer.exception.UserException;
import com.softeer.repository.UserAdapter;
import com.softeer.repository.impl.UserJpaRepository;
import com.softeer.service.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class LoginIdChecker implements Check {

    private static final int LOWER_BOUND = 6;
    private static final int UPPER_BOUND = 20;

    private final UserAdapter userAdapter;

    @Override
    public boolean check(String loginId) {
        if(!StringUtils.hasText(loginId) || !isInLength(loginId)) throw ExceptionCreator.create(UserException.LOGIN_ID_RANGE_6_20);
        if(!isConsistedOfEnglishAndDigit(loginId)) throw ExceptionCreator.create(UserException.LOGIN_ID_ONLY_ENGLISH_AND_DIGIT);
        if (userAdapter.existsByLoginId(loginId)) throw ExceptionCreator.create(UserException.DUPLICATED_LOGIN_ID, "loginId : " + loginId);
        return true;
    }

    private boolean isInLength(String loginId) {
        return  LOWER_BOUND<= loginId.length() && loginId.length() <= UPPER_BOUND;
    }
    private boolean isConsistedOfEnglishAndDigit(String loginId) {
        boolean hasEnglish = false;
        boolean hasDigit = false;

        for (char c : loginId.toCharArray()) {

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) hasEnglish = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else return false;


            if (hasEnglish && hasDigit) {
                return true;
            }
        }

        return false;
    }
}
