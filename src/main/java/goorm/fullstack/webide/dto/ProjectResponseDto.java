package goorm.fullstack.webide.dto;

import java.time.LocalDateTime;

public record ProjectResponseDto(Integer id, String name, String detail, String language,
                                 LocalDateTime createdAt, LocalDateTime updatedAt) {

}
