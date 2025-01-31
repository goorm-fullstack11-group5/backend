package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.domain.User;
import goorm.fullstack.webide.dto.FileTreeNodeDto;
import goorm.fullstack.webide.dto.ProjectRequestDto;
import goorm.fullstack.webide.repository.FileJpaRepository;
import goorm.fullstack.webide.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService implements OwnableValidation {

    private final ProjectRepository projectRepository;
    private final FileJpaRepository fileJpaRepository;

    public List<Project> getAllByUser(User user) {
        return projectRepository.findAllByUser(user);
    }

    public Project create(User user, ProjectRequestDto projectRequestDto) {
        File rootFolder = File
            .builder()
            .name(projectRequestDto.name())
            .build();
        fileJpaRepository.save(rootFolder);

        Project project = Project.builder()
            .user(user)
            .name(projectRequestDto.name())
            .detail(projectRequestDto.detail())
            .language(projectRequestDto.language())
            .rootFolder(rootFolder)
            .build();
        projectRepository.save(project);

        return project;
    }

    public void delete(Integer id) {
        Project project = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        projectRepository.delete(project);
    }

    public FileTreeNodeDto getFileTree(int projectId) {
        // 프로젝트의 루트 폴더를 최상위로 두는 하위 폴더, 파일들을 전부 가져온다.
        Project project = projectRepository.findById(projectId)
            .orElseThrow(EntityNotFoundException::new);
        File rootFolder = project.getRootFolder();

        return makeFileTree(fileJpaRepository.findAllFilesInFolder(rootFolder.getId()));
    }

    private FileTreeNodeDto makeFileTree(List<File> files) {
        Map<Integer, FileTreeNodeDto> fileTreeNodeDtoMap = new HashMap<>();

        // 먼저 조회된 모든 파일들을 FileTreeNodeDto로 변환하여 map에 저장한다.
        for (File file : files) {
            fileTreeNodeDtoMap.put(file.getId(), new FileTreeNodeDto(file));
        }

        // 그 다음 map에 추가한 값들을 이용하여 트리 구조를 생성한다.
        FileTreeNodeDto fileTree = null;
        for (File file : files) {
            FileTreeNodeDto fileTreeNodeDto = fileTreeNodeDtoMap.get(file.getId());
            // 파일의 부모가 없다는건 이 파일이 루트 폴더임을 의미
            if (!file.hasParent()) {
                fileTree = fileTreeNodeDto;
                continue;
            }
            FileTreeNodeDto parentFileTreeNodeDto = fileTreeNodeDtoMap.get(file.getParent().getId());
            parentFileTreeNodeDto.addFileTreeNodeDto(fileTreeNodeDto);
        }
        return fileTree;
    }

    @Override
    public boolean isOwner(int resourceId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project project = projectRepository.findById(resourceId)
            .orElseThrow(EntityNotFoundException::new);
        return project.getUser().getUserId() == user.getUserId();
    }
}
