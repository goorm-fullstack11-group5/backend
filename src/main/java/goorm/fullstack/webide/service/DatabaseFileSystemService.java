package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.*;
import goorm.fullstack.webide.repository.FileJpaRepository;
import goorm.fullstack.webide.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseFileSystemService implements FileSystemService {

    private final FileJpaRepository fileJpaRepository;
    private final ProjectRepository projectRepository;
    private final CodeRunner codeRunner;

    @Override
    public File createFolder(FolderRequestDto folderRequestDto) {
        File parentFolder = fileJpaRepository.findById(folderRequestDto.parentId()).orElse(null);
        File folder = File
            .builder()
            .name(folderRequestDto.name())
            .parent(parentFolder)
            .build();
        fileJpaRepository.save(folder);

        // todo: ModelMapper는 Pojo -> Record를 지원하지 못함
        //  MapStruct를 사용해보기
        return folder;
    }

    @Override
    public void deleteFolder(int id) {
        fileJpaRepository.deleteById(id);
    }

    @Override
    public File renameFolder(int id,
        FolderRenameRequestDto folderRenameRequestDto) {
        File folder = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        folder.rename(folderRenameRequestDto.name());

        fileJpaRepository.save(folder);
        return folder;
    }

    @Override
    public FileTreeNodeDto getFileTree(int projectId) {
        // todo: 파일 트리를 반환하도록 구현
        Project project = projectRepository.findById(projectId)
            .orElseThrow(EntityNotFoundException::new);
        File rootFolder = project.getRootFolder();
        return new FileTreeNodeDto(rootFolder);
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
