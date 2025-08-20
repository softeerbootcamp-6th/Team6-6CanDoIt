package com.softeer.service;

import com.softeer.domain.Image;
import com.softeer.domain.ImageMeta;
import com.softeer.entity.ImageEntity;
import com.softeer.repository.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ImageUseCaseImpl implements ImageUseCase {

    private final S3Service s3Service;
    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Long uploadImage(ImageMeta imageMeta) throws Exception {
        String imageUrl = s3Service.uploadFile(imageMeta.imageBytes(), imageMeta.fileName(), imageMeta.contentType());

        Image image = new Image(0L, imageUrl, LocalDateTime.now());
        ImageEntity imageEntity = imageJpaRepository.save(ImageEntity.from(image));

        return imageEntity.getId();
    }
}
