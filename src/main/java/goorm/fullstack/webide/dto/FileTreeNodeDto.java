package goorm.fullstack.webide.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import goorm.fullstack.webide.domain.File;
import java.util.List;

public record FileTreeNodeDto(int id, String name, Boolean isFolder, @JsonInclude(Include.NON_NULL) List<FileTreeNodeDto> files) {
    public FileTreeNodeDto {
        if (!isFolder) {
            files = null;
        }
    }

    public FileTreeNodeDto(File file) {
        this(file.getId(), file.getName(), file.getIsFolder(),
            file.getFiles().stream().map(FileTreeNodeDto::new).toList());
    }
}
