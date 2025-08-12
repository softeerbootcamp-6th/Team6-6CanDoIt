package com.softeer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import com.softeer.domain.Image;

@Table(name = "image")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "image_url", length = 2048)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public static ImageEntity from(Image image) {
        return new ImageEntity(image.id(), image.imageUrl(), image.createdAt());
    }

    public static Image toDomain(ImageEntity imageEntity) {
        return new Image(imageEntity.id, imageEntity.imageUrl,  imageEntity.createdAt);
    }
}
