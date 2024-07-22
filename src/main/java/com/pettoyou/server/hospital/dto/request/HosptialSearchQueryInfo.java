package com.pettoyou.server.hospital.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.pettoyou.server.store.entity.Store}
 */
public record HosptialSearchQueryInfo(@NotNull @Size(min = 2)  String storeName) implements Serializable {
}