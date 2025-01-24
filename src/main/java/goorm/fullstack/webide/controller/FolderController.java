package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.dto.FolderRenameRequestDto;
import goorm.fullstack.webide.dto.FolderRequestDto;
import goorm.fullstack.webide.service.FileFolderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{id}/folders")
@RequiredArgsConstructor
public class FolderController {
    /**
     * todo: FolderController에 FolderService가 있어야 적절하지만 FileFolderService가 있어서 의문이 든다.
     *  FileFolderService를 분리하여 FileService와 FolderService로 나누고, 각각 Bean으로 등록하자.
     */
    private final FileFolderService fileFolderService;

    @PostMapping
    public FileResponseDto createFolder(@RequestBody FolderRequestDto folderRequestDto) {
        return fileFolderService.createFolder(folderRequestDto);
    }

    @DeleteMapping("/{folderId}")
    public void deleteFolder(@PathVariable("folderId") Integer id) {
        fileFolderService.deleteFolder(id);
    }

    @PatchMapping("/{folderId}")
    public List<FileResponseDto> renameFolder(@PathVariable("folderId") Integer id, @RequestBody FolderRenameRequestDto folderRenameRequestDto) {
        return fileFolderService.renameFolder(id, folderRenameRequestDto);
    }
}
