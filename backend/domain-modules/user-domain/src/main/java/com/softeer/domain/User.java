package com.softeer.domain;

import com.softeer.entity.Role;

public record User(long id, String nickname, String loginId, String password, Role role, String imageUrl) {

}
