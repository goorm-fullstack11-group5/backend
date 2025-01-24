package goorm.fullstack.webide.dto;

import java.time.LocalDateTime;

public record FileResponseDto(Integer id, String name, String content, LocalDateTime createdAt,
                              LocalDateTime updatedAt) {

}
