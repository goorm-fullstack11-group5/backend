package goorm.fullstack.webide.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import goorm.fullstack.webide.domain.File;
import java.util.List;

public record FileTreeNodeDto(int id, String name, @JsonInclude(Include.NON_NULL) List<FileTreeNodeDto> files) {
    public FileTreeNodeDto {
        if (files.isEmpty()) {
            files = null;
        }
    }

    public FileTreeNodeDto(File folder) {
        this(folder.getId(), folder.getName(),
            folder.getFiles().stream().map(FileTreeNodeDto::new).toList());
    }
}
