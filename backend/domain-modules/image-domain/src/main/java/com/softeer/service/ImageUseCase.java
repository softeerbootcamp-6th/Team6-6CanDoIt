package com.softeer.service;

import com.softeer.domain.ImageMeta;

public interface ImageUseCase {

    Long uploadImage(ImageMeta imageMeta) throws Exception;
}
