package goorm.fullstack.webide.service;

import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.ProjectRequestDto;
import goorm.fullstack.webide.dto.ProjectResponseDto;
import goorm.fullstack.webide.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectResponseDto> getAllByUser(UserDetails userDetails) {
        // todo: 테스트를 위해 테이블에 저장된 전체 프로젝트를 반환하도록 설정함.
        //       회원가입 기능이 구현된다면 특정 유저의 프로젝트를 반환하도록 설정해야함.
        return projectRepository.findAll().stream().map(Project::toDto).toList();
    }

    public ProjectResponseDto create(ProjectRequestDto projectRequestDto) {
        Project project = Project.builder()
                .name(projectRequestDto.name())
                .detail(projectRequestDto.detail())
                .build();
        projectRepository.save(project);
        return project.toDto();
    }
}
