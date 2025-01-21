package goorm.fullstack.webide.dto;

import java.util.List;

public record FileTreeNodeDto(String name, List<FileTreeNodeDto> fileTreeNodeDto) {
}
