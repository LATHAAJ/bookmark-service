package com.anywhereworks.bookmark.controller;

import com.anywhereworks.bookmark.dto.FolderDto;
import com.anywhereworks.bookmark.entity.Folder;
import com.anywhereworks.bookmark.service.impl.FolderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

  private FolderServiceImpl folderService;

    public FolderController(FolderServiceImpl folderService) {
        this.folderService = folderService;
    }

  @PostMapping
  public ResponseEntity<Folder> createFolder(FolderDto folderDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(folderService.createFolder(folderDto));
  }
  @GetMapping
  public ResponseEntity<List<Folder>> getFolder() {
    return ResponseEntity.ok(folderService.fetchAllFolders());
  }

  @PutMapping("/folderId")
  public ResponseEntity<Folder> updateFolder(@RequestBody FolderDto folderDto, @PathVariable Long folderId) {
    return ResponseEntity.ok(folderService.updateFolder(folderDto, folderId));
  }

  @DeleteMapping("/{folderId}")
  public ResponseEntity<Void> deleteFolder(@PathVariable String folderId) {
    folderService.deleteFolderById(Long.parseLong(folderId));
    return ResponseEntity.noContent().build();
  }

}
