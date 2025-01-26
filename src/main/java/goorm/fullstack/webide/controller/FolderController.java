package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.dto.FolderRenameRequestDto;
import goorm.fullstack.webide.dto.FolderRequestDto;
import goorm.fullstack.webide.service.FolderService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<FileResponseDto> createFolder(@PathVariable("projectId") int projectId,
        @RequestBody FolderRequestDto folderRequestDto) {
        File folder = folderService.createFolder(folderRequestDto);
        URI uri = URI.create("/projects/" + projectId + "/folders/" + folder.getId());

        return ResponseEntity.created(uri).body(folder.toDto());
    }

    @PatchMapping("/{folderId}")
    public ResponseEntity<FileResponseDto> renameFolder(@PathVariable("folderId") int id,
        @RequestBody FolderRenameRequestDto folderRenameRequestDto) {
        File renamedFolder = folderService.renameFolder(id, folderRenameRequestDto);

        return ResponseEntity.ok(renamedFolder.toDto());
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable("folderId") int id) {
        folderService.deleteFolder(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
