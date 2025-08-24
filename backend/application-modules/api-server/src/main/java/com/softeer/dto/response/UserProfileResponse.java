package com.softeer.dto.response;

import com.softeer.domain.User;

public record UserProfileResponse(String nickname, String imageUrl) {

    public UserProfileResponse(User user) {
        this(user.nickname(), user.imageUrl());
    }
}
