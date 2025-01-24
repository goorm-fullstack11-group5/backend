package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.dto.FileTreeNodeDto;
import goorm.fullstack.webide.dto.FolderRenameRequestDto;
import goorm.fullstack.webide.dto.FolderRequestDto;
import java.util.List;

public interface FolderService {

    FileResponseDto createFolder(FolderRequestDto folderRequestDto);

    void deleteFolder(int id);

    List<FileResponseDto> renameFolder(int id, FolderRenameRequestDto folderRenameRequestDto);

    FileTreeNodeDto getFileTree(Project project);

}
