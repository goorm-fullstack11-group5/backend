package goorm.fullstack.webide.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import goorm.fullstack.webide.domain.File;
import java.util.ArrayList;
import java.util.List;

public record FileTreeNodeDto(int id, String name, Boolean isFolder,
                              @JsonInclude(Include.NON_NULL) List<FileTreeNodeDto> files) {
    public FileTreeNodeDto(File file) {
        // todo: 파라미터가 폴더일 때 ArrayList를 만들게 되는데, runCode에서 지적받았던 것처럼
        //  폴더 안에 하위 폴더나 파일이 엄청 많을 때 계속 array를 두 배로 늘리는 작업이 예상된다.
        this(file.getId(), file.getName(), file.getIsFolder(), file.getIsFolder() ? new ArrayList<>() : null);
    }

    public void addFileTreeNodeDto(FileTreeNodeDto fileTreeNodeDto) {
        files.add(fileTreeNodeDto);

    }
}
