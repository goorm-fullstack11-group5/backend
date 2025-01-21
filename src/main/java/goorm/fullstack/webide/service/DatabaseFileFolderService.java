package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.FileRequestDto;
import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.dto.FileTreeNodeDto;
import goorm.fullstack.webide.repository.FileJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DatabaseFileFolderService implements FileFolderService {
    private final FileJpaRepository fileJpaRepository;

    @Override
    public FileResponseDto createFile(FileRequestDto fileRequestDto) {
        File file = File.builder()
                .name(fileRequestDto.name())
                .path(fileRequestDto.path())
                .content(fileRequestDto.content())
                .isFolder(false)
                .build();

        fileJpaRepository.save(file);
        return file.toDto();
    }

    @Override
    public void deleteFile(Integer id) {
        // todo: 파일 삭제 구현
        File file = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        fileJpaRepository.delete(file);
    }

    @Override
    public FileResponseDto readFileContent(Integer id) {
        return fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new).toDto();
    }

    @Override
    public FileResponseDto updateFileContent(Integer id, String newContent) {
        File file = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        file.setContent(newContent);

        fileJpaRepository.save(file);
        return file.toDto();
    }

    @Override
    @Transactional
    public FileResponseDto renameFile(Integer id, String newName) {
        File file = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        file.setName(newName);

        fileJpaRepository.save(file);
        return file.toDto();
    }

    @Override
    public void createFolder(String path, String name) {
        // todo: 특정 경로에 폴더를 생성하도록 구현
    }

    @Override
    public void deleteFolder(String path) {
        // todo: 폴더 삭제 시 하위 폴더 및 파일들도 삭제하도록 구현
    }

    @Override
    @Transactional
    public void renameFolder(String path, String newName) {
        // todo: 폴더 경로를 변경할 때 하위 폴더 및 파일들의 경로도 변경되도록 구현
    }

    @Override
    public FileTreeNodeDto getFileTree(Project project) {
        // todo: 파일 트리를 반환하도록 구현
        return null;
    }

    @Override
    public String executeFile(String path) {
        // todo: 파일 실행 후 결과를 반환하도록 구현
        return null;
    }
}
