package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.dto.FileTreeNodeDto;
import goorm.fullstack.webide.dto.ProjectRequestDto;
import goorm.fullstack.webide.dto.ProjectResponseDto;
import goorm.fullstack.webide.service.ProjectService;
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
    public List<ProjectResponseDto> getAllProject() {
        return projectService.getAllByUser(null);
    }

    @PostMapping
    public ProjectResponseDto createProject(@RequestBody ProjectRequestDto projectRequestDto) {
        return projectService.create(projectRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") Integer id) {
        projectService.delete(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FileTreeNodeDto> getFileTree(@PathVariable("id") int projectId) {
        return ResponseEntity.ok(projectService.getFileTree(projectId));
    }
}
