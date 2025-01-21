package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.*;

/**
 * 파일을 데이터베이스에 저장하는 방법을 먼저 구현하고, 추후 파일 시스템이나 다른 방법을 사용해 구현하는
 *  경우를 생각하여 인터페이스로 추상화함.
 */
public interface FileFolderService {

    // 파일 관련 기능
    FileResponseDto createFile(FileRequestDto fileRequestDto);
    void deleteFile(Integer id);
    FileResponseDto readFileContent(Integer id);
    FileResponseDto updateFileContent(Integer id, String newContent);
    FileResponseDto renameFile(Integer id, String newName);

    // 폴더 관련 기능
    FileResponseDto createFolder(FolderRequestDto folderRequestDto);
    void deleteFolder(Integer id);
    FileResponseDto renameFolder(Integer id, FolderRenameRequestDto folderRenameRequestDto);

    // 파일 트리 반환
    FileTreeNodeDto getFileTree(Project project);

    // 파일 실행
    String executeFile(String path);
}
