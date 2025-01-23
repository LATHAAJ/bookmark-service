package com.anywhereworks.bookmark.controller;

import com.anywhereworks.bookmark.dto.BookmarkDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/bookmarks")
public class BookmarkController {

  @PostMapping
  public ResponseEntity<BookmarkDto> createBookmark(BookmarkDto bookmarkDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkDto);
  }

  @PutMapping("/{bookmarkId}")
  public ResponseEntity<BookmarkDto> updateBookmark(@PathVariable Long bookmarkId, @RequestBody BookmarkDto bookmarkDto) {
    return ResponseEntity.ok(bookmarkDto);
  }

  @DeleteMapping("/{bookmarkId}")
  public ResponseEntity<Void> deleteBookmark(@PathVariable Long bookmarkId) {
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<List<BookmarkDto>> getAllBookMarks() {
    return ResponseEntity.ok(List.of());
  }

}
