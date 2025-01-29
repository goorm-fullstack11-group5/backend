package goorm.fullstack.webide.dto;

public record FileRequestDto(int parentFolderId, String name, String content) {
}
