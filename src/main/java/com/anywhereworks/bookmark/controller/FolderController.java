package com.anywhereworks.bookmark.controller;

import com.anywhereworks.bookmark.dto.FolderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

  @PostMapping("/add-folder")
  public ResponseEntity<FolderDto> createFolder(FolderDto folderDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(folderDto);
  }

  @GetMapping
  public ResponseEntity<List<FolderDto>> getFolder() {
    return ResponseEntity.ok(List.of());
  }

  @PutMapping("/update-folder")
  public ResponseEntity<FolderDto> updateFolder(@RequestBody FolderDto folderDto) {
    return ResponseEntity.ok(folderDto);
  }

  @DeleteMapping("/delete-folder/{folderId}")
  public ResponseEntity<Void> deleteFolder(@PathVariable String folderId) {
    return ResponseEntity.noContent().build();
  }

}
