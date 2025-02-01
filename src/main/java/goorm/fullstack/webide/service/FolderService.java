package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.User;
import goorm.fullstack.webide.dto.FolderMoveRequestDto;
import goorm.fullstack.webide.dto.FolderRenameRequestDto;
import goorm.fullstack.webide.dto.FolderRequestDto;

public interface FolderService extends OwnableValidation {

    File createFolder(User user, int projectId, FolderRequestDto folderRequestDto);

    void deleteFolder(int id);

    File renameFolder(int id, FolderRenameRequestDto folderRenameRequestDto);

    void moveFolder(int id, FolderMoveRequestDto parentId);
}
