package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.domain.User;
import goorm.fullstack.webide.dto.FileTreeNodeDto;
import goorm.fullstack.webide.dto.ProjectRequestDto;
import goorm.fullstack.webide.repository.FileJpaRepository;
import goorm.fullstack.webide.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

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
        Project project = projectRepository.findById(projectId)
            .orElseThrow(EntityNotFoundException::new);
        File rootFolder = project.getRootFolder();
        return new FileTreeNodeDto(rootFolder);
    }

    public boolean hasProject(int projectId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        return project.getUser().getUserId() == user.getUserId();
    }
}
