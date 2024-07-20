package com.pettoyou.server.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.pettoyou.server.photo.entity.PhotoData}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PhotoDataDto(String photoUrl) implements Serializable {
}