package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.*;
import goorm.fullstack.webide.repository.FileJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DatabaseFileFolderService implements FileFolderService {
    private final FileJpaRepository fileJpaRepository;

    @Override
    public FileResponseDto createFile(FileRequestDto fileRequestDto) {
        File file = File.builder().name(fileRequestDto.name()).path(fileRequestDto.path()).content(fileRequestDto.content()).isFolder(false).build();

        fileJpaRepository.save(file);
        return file.toDto();
    }

    @Override
    public void deleteFile(Integer id) {
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
    public FileResponseDto createFolder(FolderRequestDto folderRequestDto) {
        // todo: 특정 경로에 폴더를 생성하도록 구현
        File file = File.builder().path(folderRequestDto.path()).name(folderRequestDto.name()).isFolder(true).content(null).build();
        fileJpaRepository.save(file);

        return file.toDto();
    }

    @Override
    @Transactional
    public void deleteFolder(Integer id) {
        // todo: 폴더 삭제 시 하위 폴더 및 파일들도 삭제하도록 구현
        File folder = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!folder.isFolder()) {
            // todo: 적절한 예외 처리 클래스 사용
            throw new EntityNotFoundException("");
        }

        String folderPath = folder.getPath();
        String path = folderPath + folder.getName();
        fileJpaRepository.deleteInPathStartingWith(path);
        fileJpaRepository.delete(folder);
    }

    @Override
    public List<FileResponseDto> renameFolder(Integer id, FolderRenameRequestDto folderRenameRequestDto) {
        File folder = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String folderPath = folder.getPath() + folder.getName();
        String newPath = folder.getPath() + folderRenameRequestDto.name();
        folder.setName(folderRenameRequestDto.name());

        List<File> renamedFiles = fileJpaRepository.findAllByPathStartingWith(folderPath)
                .stream()
                .map(file -> {
                    file.setPath(file.getPath().replace(folderPath, newPath));
                    return file;
                }).toList();

        fileJpaRepository.save(folder);
        fileJpaRepository.saveAll(renamedFiles);
        return renamedFiles.stream().map(File::toDto).toList();
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
