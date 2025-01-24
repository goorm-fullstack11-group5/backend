package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.*;
import goorm.fullstack.webide.repository.FileJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseFileSystemService implements FileSystemService {

    private final FileJpaRepository fileJpaRepository;
    private final CodeRunner codeRunner;


    @Override
    public FileResponseDto createFolder(FolderRequestDto folderRequestDto) {
        return null;
    }

    @Override
    public void deleteFolder(int id) {

    }

    @Override
    public List<FileResponseDto> renameFolder(int id,
        FolderRenameRequestDto folderRenameRequestDto) {
        return List.of();
    }

    @Override
    public FileTreeNodeDto getFileTree(Project project) {
        // todo: 파일 트리를 반환하도록 구현
        return null;
    }

    @Override
    public FileResponseDto createFile(FileRequestDto fileRequestDto) {
        return null;
    }

    @Override
    public void deleteFile(int id) {

    }

    @Override
    public FileResponseDto readFileContent(int id) {
        return null;
    }

    @Override
    public FileResponseDto updateFileContent(int id, String newContent) {
        return null;
    }

    @Override
    public FileResponseDto renameFile(int id, String newName) {
        return null;
    }

    @Override
    public String runFile(int id) {
        // todo: 파일 실행 후 결과를 반환하도록 구현
        File file = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return codeRunner.run(file);
    }
}
