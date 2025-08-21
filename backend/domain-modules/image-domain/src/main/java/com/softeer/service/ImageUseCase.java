package com.softeer.service;

import com.softeer.domain.ImageMeta;

public interface ImageUseCase {

    long uploadImage(ImageMeta imageMeta) throws Exception;
}
