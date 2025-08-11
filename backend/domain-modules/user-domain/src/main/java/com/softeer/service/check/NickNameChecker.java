package com.softeer.service.check;

import com.softeer.error.ExceptionCreator;
import com.softeer.exception.UserException;
import com.softeer.repository.UserAdapter;
import com.softeer.service.Check;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class NickNameChecker implements Check {

    private static final int LOWER_BOUND = 2;
    private static final int UPPER_BOUND = 20;

    private static final char LOWER_KOREAN_BOUND = '\uAC00'; // 가
    private static final char UPPER_KOREAN_BOUND = '\uD7A3'; // 힣

    private final UserAdapter userAdapter;

    @Override
    public boolean check(String nickname) {
        if (!StringUtils.hasText(nickname) || !isInLength(nickname)) throw ExceptionCreator.create(UserException.NICKNAME_RANGE_2_20);
        if (!isConsistedOfKorean(nickname)) throw ExceptionCreator.create(UserException.NICKNAME_ONLY_KOREAN);
        if (userAdapter.existsByNickname(nickname)) throw ExceptionCreator.create(UserException.DUPLICATED_NICKNAME, "nickname : " + nickname);


        return true;
    }

    private boolean isConsistedOfKorean(String nickname) {
        for (char c : nickname.toCharArray()) {
            if (c < LOWER_KOREAN_BOUND || c > UPPER_KOREAN_BOUND) return false;
        }
        return true;
    }

    private boolean isInLength(String nickname) {
        return  LOWER_BOUND<= nickname.length() && nickname.length() <= UPPER_BOUND;
    }
}
