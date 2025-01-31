package goorm.fullstack.webide.controller;

import goorm.fullstack.webide.domain.File;
import goorm.fullstack.webide.dto.FileResponseDto;
import goorm.fullstack.webide.dto.FileTreeNodeDto;
import goorm.fullstack.webide.dto.FolderMoveRequestDto;
import goorm.fullstack.webide.dto.FolderRenameRequestDto;
import goorm.fullstack.webide.dto.FolderRequestDto;
import goorm.fullstack.webide.service.FolderService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    @PreAuthorize("@projectService.isOwner(#projectId)")
    public ResponseEntity<FileResponseDto> createFolder(@PathVariable("projectId") int projectId,
        @RequestBody FolderRequestDto folderRequestDto) {
        File folder = folderService.createFolder(folderRequestDto);
        URI uri = URI.create("/projects/" + projectId + "/folders/" + folder.getId());

        return ResponseEntity.created(uri).body(folder.toDto());
    }

    @PatchMapping("/{folderId}")
    @PreAuthorize("@projectService.isOwner(#projectId) && @folderService.isOwner(#id)")
    public ResponseEntity<FileResponseDto> renameFolder(
        @PathVariable("projectId") int projectId,
        @PathVariable("folderId") int id,
        @RequestBody FolderRenameRequestDto folderRenameRequestDto) {
        File renamedFolder = folderService.renameFolder(id, folderRenameRequestDto);

        return ResponseEntity.ok(renamedFolder.toDto());
    }

    @DeleteMapping("/{folderId}")
    @PreAuthorize("@projectService.isOwner(#projectId) && @folderService.isOwner(#id)")
    public ResponseEntity<Void> deleteFolder(
        @PathVariable("projectId") int projectId,
        @PathVariable("folderId") int id) {
        folderService.deleteFolder(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PatchMapping("/{folderId}/move")
    @PreAuthorize("@projectService.isOwner(#projectId) && @folderService.isOwner(#folderId) && @folderService.isOwner(#folderMoveRequestDto.parentFolderId())")
    public ResponseEntity<Void> moveFolder(
        @PathVariable("projectId") int projectId,
        @PathVariable("folderId") int folderId,
        @RequestBody FolderMoveRequestDto folderMoveRequestDto) {
        folderService.moveFolder(folderId, folderMoveRequestDto);
        return ResponseEntity.noContent().build();
    }
}
