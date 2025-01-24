package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.dto.FileContentRequestDto;
import goorm.fullstack.webide.dto.FileNameRequestDto;
import goorm.fullstack.webide.dto.FileRequestDto;
import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.service.FileService;
import lombok.RequiredArgsConstructor;
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
    public FileResponseDto readFile(@PathVariable("fileId") Integer fileId) {
        return fileService.readFileContent(fileId);
    }

    @PostMapping
    public FileResponseDto createFile(@RequestBody FileRequestDto fileRequestDto) {
        return fileService.createFile(fileRequestDto);
    }

    @DeleteMapping("/files/{fileId}")
    public void deleteFile(@PathVariable("fileId") Integer id) {
        fileService.deleteFile(id);
    }

    @PatchMapping("/files/{fileId}")
    public FileResponseDto updateFileContent(@PathVariable("fileId") Integer id,
        @RequestBody FileContentRequestDto fileContentRequestDto) {
        return fileService.updateFileContent(id, fileContentRequestDto.content());
    }

    @PostMapping("/files/{fileId}/rename")
    public FileResponseDto updateFileName(@PathVariable("fileId") Integer id,
        @RequestBody FileNameRequestDto fileNameRequestDto) {
        return fileService.renameFile(id, fileNameRequestDto.name());
    }
}
