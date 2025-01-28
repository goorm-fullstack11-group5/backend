package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.dto.CodeResultDto;
import goorm.fullstack.webide.dto.FileUpdateRequestDto;
import goorm.fullstack.webide.dto.FileRequestDto;
import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.service.FileService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * todo: 경로 변수로 주어진 projectId가 가리키는 Project가 해당 사용자의 소유인지 검증하는 로직 필요
 */
@RestController
@RequestMapping("/projects/{projectId}/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDto> readFile(@PathVariable("fileId") int fileId) {
        File file = fileService.readFileContent(fileId);
        return ResponseEntity.ok(file.toDto());
    }

    @PostMapping
    public ResponseEntity<FileResponseDto> createFile(@PathVariable("projectId") int projectId,
        @RequestBody FileRequestDto fileRequestDto) {
        File file = fileService.createFile(fileRequestDto);
        URI uri = URI.create("/projects/" + projectId + "/files/" + file.getId());
        return ResponseEntity.created(uri).body(file.toDto());
    }

    @DeleteMapping("/{fileId}")
    public void deleteFile(@PathVariable("fileId") int id) {
        fileService.deleteFile(id);
    }

    @PatchMapping("/{fileId}")
    public ResponseEntity<FileResponseDto> updateFile(@PathVariable("fileId") int id,
        @RequestBody FileUpdateRequestDto fileUpdateRequestDto) {
        return ResponseEntity.ok(
            fileService.updateFile(id, fileUpdateRequestDto).toDto());
    }

    @PostMapping("/{fileId}/run")
    public ResponseEntity<CodeResultDto> runCode(@PathVariable("fileId") int id) {
        return ResponseEntity.ok(new CodeResultDto(fileService.runFile(id)));
    }
}
