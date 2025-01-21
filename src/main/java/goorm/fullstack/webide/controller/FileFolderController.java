package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.dto.FileContentRequestDto;
import goorm.fullstack.webide.dto.FileNameRequestDto;
import goorm.fullstack.webide.dto.FileRequestDto;
import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.service.FileFolderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * todo: 경로 변수로 주어진 projectId가 가리키는 Project가 해당 사용자의 소유인지 검증하는 로직 필요
 */
@RestController
@RequestMapping("/projects/{projectId}")
@AllArgsConstructor
public class FileFolderController {
    private final FileFolderService fileFolderService;

    @GetMapping("/files/{fileId}")
    public FileResponseDto readFile(@PathVariable("fileId") Integer fileId) {
        return fileFolderService.readFileContent(fileId);
    }

    @PostMapping("/files")
    public FileResponseDto createFile(@RequestBody FileRequestDto fileRequestDto) {
        return fileFolderService.createFile(fileRequestDto);
    }

    @DeleteMapping("/files/{fileId}")
    public void deleteFile(@PathVariable("fileId") Integer id) {
        fileFolderService.deleteFile(id);
    }

    @PatchMapping("/files/{fileId}")
    public FileResponseDto updateFileContent(@PathVariable("fileId") Integer id, @RequestBody FileContentRequestDto fileContentRequestDto) {
        return fileFolderService.updateFileContent(id, fileContentRequestDto.content());
    }

    @PostMapping("/files/{fileId}/rename")
    public FileResponseDto updateFileName(@PathVariable("fileId") Integer id, @RequestBody FileNameRequestDto fileNameRequestDto) {
        return fileFolderService.renameFile(id, fileNameRequestDto.name());
    }
}
