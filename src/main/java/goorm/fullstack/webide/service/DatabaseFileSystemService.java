package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.User;
import goorm.fullstack.webide.dto.*;
import goorm.fullstack.webide.repository.FileJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class DatabaseFileSystemService implements FileSystemService {

    private final FileJpaRepository fileJpaRepository;
    private final CodeRunner codeRunner;

    @Override
    public File createFolder(FolderRequestDto folderRequestDto) {
        File parentFolder = fileJpaRepository.findById(folderRequestDto.parentId()).orElse(null);
        File folder = File
            .builder()
            .name(folderRequestDto.name())
            .parent(parentFolder)
            .isFolder(true)
            .build();
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
    public void moveFolder(int id, FolderMoveRequestDto folderMoveRequestDto) {
        int parentId = folderMoveRequestDto.parentFolderId();
        if (parentId == id) {
            return;
        }

        File parentFolder = fileJpaRepository.findById(parentId).orElseThrow(EntityNotFoundException::new);
        File targetFolder = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        // 이동시키려는 폴더(targetFolder)가 목적지(parentFolder)를 하위 폴더로 갖는 경우는 이동이 불가능하게 해야함.
        File iter = parentFolder;
        while(iter.getParent() != null) {
            if (iter == targetFolder) {
                return;
            }
            iter = iter.getParent();
        }
        targetFolder.moveTo(parentFolder);

        fileJpaRepository.save(targetFolder);
    }

    @Override
    public File createFile(FileRequestDto fileRequestDto) {
        File parentFolder = fileJpaRepository.findById(fileRequestDto.parentFolderId())
            .orElse(null);

        File file = File
            .builder()
            .name(fileRequestDto.name())
            .content(fileRequestDto.content())
            .isFolder(false)
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
        File file = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return codeRunner.run(file);
    }

    @Override
    public void moveFile(int id, FileMoveRequestDto fileMoveRequestDto) {
        int parentFolderIdId = fileMoveRequestDto.parentFolderId();
        File parentFolder = fileJpaRepository.findById(parentFolderIdId).orElseThrow(EntityNotFoundException::new);
        File targetFile  = fileJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        targetFile.moveTo(parentFolder);

        fileJpaRepository.save(targetFile);
    }

    @Override
    public boolean isOwner(int resourceId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        File file = fileJpaRepository.findById(resourceId)
            .orElseThrow(EntityNotFoundException::new);
        return file.getUser().getUserId() == user.getUserId();
    }
}
