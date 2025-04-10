package com.anywhereworks.bookmark.controller;

import com.anywhereworks.bookmark.dto.FolderDto;
import com.anywhereworks.bookmark.entity.Folder;
import com.anywhereworks.bookmark.service.FolderService;
import com.anywhereworks.bookmark.service.impl.FolderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/folders")
public class FolderController {

  private final FolderService folderService;

  @PostMapping
  public ResponseEntity<Folder> createFolder(FolderDto folderDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(folderService.createFolder(folderDto));
  }
  @GetMapping
  public ResponseEntity<List<Folder>> getFolders() {
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
