package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.dto.FolderRenameRequestDto;
import goorm.fullstack.webide.dto.FolderRequestDto;
import goorm.fullstack.webide.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{id}/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public FileResponseDto createFolder(@RequestBody FolderRequestDto folderRequestDto) {
        return folderService.createFolder(folderRequestDto);
    }

    @DeleteMapping("/{folderId}")
    public void deleteFolder(@PathVariable("folderId") Integer id) {
        folderService.deleteFolder(id);
    }

    @PatchMapping("/{folderId}")
    public List<FileResponseDto> renameFolder(@PathVariable("folderId") Integer id,
        @RequestBody FolderRenameRequestDto folderRenameRequestDto) {
        return folderService.renameFolder(id, folderRenameRequestDto);
    }
}
