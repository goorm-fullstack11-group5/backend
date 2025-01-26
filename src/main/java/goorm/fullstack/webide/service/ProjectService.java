package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.FileTreeNodeDto;
import goorm.fullstack.webide.dto.ProjectRequestDto;
import goorm.fullstack.webide.dto.ProjectResponseDto;
import goorm.fullstack.webide.repository.FileJpaRepository;
import goorm.fullstack.webide.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final FileJpaRepository fileJpaRepository;

    public List<Project> getAllByUser(UserDetails userDetails) {
        // todo: 테스트를 위해 테이블에 저장된 전체 프로젝트를 반환하도록 설정함.
        //       회원가입 기능이 구현된다면 특정 유저의 프로젝트를 반환하도록 설정해야함.
        return projectRepository.findAll();
    }

    public Project create(ProjectRequestDto projectRequestDto) {
        File rootFolder = File
            .builder()
            .name(projectRequestDto.name())
            .build();
        fileJpaRepository.save(rootFolder);

        Project project = Project.builder()
            .name(projectRequestDto.name())
            .detail(projectRequestDto.detail())
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
}
