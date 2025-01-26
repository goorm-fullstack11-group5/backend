package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.domain.Project;
import goorm.fullstack.webide.dto.FileTreeNodeDto;
import goorm.fullstack.webide.dto.ProjectRequestDto;
import goorm.fullstack.webide.dto.ProjectResponseDto;
import goorm.fullstack.webide.service.ProjectService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProject() {
        return ResponseEntity.ok(
            projectService.getAllByUser(null).stream().map(Project::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(
        @RequestBody ProjectRequestDto projectRequestDto) {
        Project project = projectService.create(projectRequestDto);
        URI uri = URI.create("/projects/" + project.getId());

        return ResponseEntity.created(uri).body(project.toDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Integer id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<FileTreeNodeDto> getFileTree(@PathVariable("id") int projectId) {
        return ResponseEntity.ok(projectService.getFileTree(projectId));
    }
}
