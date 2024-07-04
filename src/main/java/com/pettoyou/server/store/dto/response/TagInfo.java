package com.pettoyou.server.store.dto.response;

import java.util.List;

public record TagInfo(
        List<String> services,
        List<String> businessHours,
        List<String> specialists,
        String emergencyStatus
) {
}
