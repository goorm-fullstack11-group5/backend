package goorm.fullstack.webide.dto;

import java.time.LocalDateTime;

public record ProjectResponseDto(Integer id, String name, String detail, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
