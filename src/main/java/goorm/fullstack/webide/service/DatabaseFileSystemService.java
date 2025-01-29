package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.User;
import goorm.fullstack.webide.dto.*;
import goorm.fullstack.webide.repository.FileJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseFileSystemService implements FileSystemService {

    private final FileJpaRepository fileJpaRepository;
    private final CodeRunner codeRunner;

    @Override
    public File createFolder(FolderRequestDto folderRequestDto) {
        File parentFolder = fileJpaRepository.findById(folderRequestDto.parentId()).orElse(null);
        File folder = File.builder().name(folderRequestDto.name()).parent(parentFolder).build();
        fileJpaRepository.save(folder);

        // todo: ModelMapper는 Pojo -> Record를 지원하지 못함
        //  MapStruct를 사용해보기
        return folder;
    }

    @Override
    public void deleteFolder(int id) {
        // todo: 루트 폴더는 지울 수 없는 예외 처리
        fileJpaRepository.deleteById(id);
    }

    @Override
    public File renameFolder(int id, FolderRenameRequestDto folderRenameRequestDto) {
        File folder = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        folder.rename(folderRenameRequestDto.name());

        fileJpaRepository.save(folder);
        return folder;
    }

    @Override
    public File createFile(FileRequestDto fileRequestDto) {
        File parentFolder = fileJpaRepository.findById(fileRequestDto.parentFolderId())
            .orElse(null);

        File file = File.builder().name(fileRequestDto.name()).content(fileRequestDto.content())
            .parent(parentFolder).build();

        return fileJpaRepository.save(file);
    }

    @Override
    public void deleteFile(int id) {
        fileJpaRepository.deleteById(id);
    }

    @Override
    public File readFileContent(int id) {
        File file = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return file;
    }

    @Override
    public File updateFile(int id, FileUpdateRequestDto fileUpdateRequestDto) {
        File file = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String newName = fileUpdateRequestDto.name();
        String newContent = fileUpdateRequestDto.content();

        if (newName != null) {
            file.rename(newName);
        }
        if (newContent != null) {
            file.updateContent(newContent);
        }

        return fileJpaRepository.save(file);
    }

    @Override
    public String runFile(int id) {
        // todo: 파일 실행 후 결과를 반환하도록 구현
        File file = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return codeRunner.run(file);
    }

    @Override
    public boolean isOwner(int resourceId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        File file = fileJpaRepository.findById(resourceId)
            .orElseThrow(EntityNotFoundException::new);
        return file.getUser().getUserId() == user.getUserId();
    }
}
