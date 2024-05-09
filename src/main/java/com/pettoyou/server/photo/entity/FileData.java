package com.pettoyou.server.photo.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileData {

    private String bucket;
    private String object;
    private String photoUrl;


    public static FileData of(String bucket, String object, String photoUrl) {
        return new FileData(bucket, object, photoUrl);
    }
}
