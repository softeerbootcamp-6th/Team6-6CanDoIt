package com.softeer.repository;

import com.softeer.domain.Mountain;

import java.util.List;

public interface MountainAdapter {

    List<Mountain> findAllMountains();
    Mountain findMountainById(long id);
}
