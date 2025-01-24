package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.*;
import goorm.fullstack.webide.repository.FileJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DatabaseFileFolderService implements FileFolderService {
    private final FileJpaRepository fileJpaRepository;
    private final CodeRunner codeRunner;

    @Override
    public FileResponseDto createFile(FileRequestDto fileRequestDto) {
        return null;
    }

    @Override
    public void deleteFile(Integer id) {

    }

    @Override
    public FileResponseDto readFileContent(Integer id) {
        return null;
    }

    @Override
    public FileResponseDto updateFileContent(Integer id, String newContent) {
        return null;
    }

    @Override
    public FileResponseDto renameFile(Integer id, String newName) {
        return null;
    }

    @Override
    public FileResponseDto createFolder(FolderRequestDto folderRequestDto) {
        return null;
    }

    @Override
    public void deleteFolder(Integer id) {

    }

    @Override
    public List<FileResponseDto> renameFolder(Integer id, FolderRenameRequestDto folderRenameRequestDto) {
        return List.of();
    }

    @Override
    public FileTreeNodeDto getFileTree(Project project) {
        // todo: 파일 트리를 반환하도록 구현
        return null;
    }

    @Override
    public String executeFile(String path) {
        // todo: 파일 실행 후 결과를 반환하도록 구현
        File file = fileJpaRepository.findByPath(path).orElseThrow(EntityNotFoundException::new);
        return codeRunner.run(file);
    }
}


// LIKE 성능이 좋지 않으므로, 계층형 테이블 구조로 변경해야함
