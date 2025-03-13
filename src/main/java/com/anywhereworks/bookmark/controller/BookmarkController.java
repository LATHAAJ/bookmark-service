package com.anywhereworks.bookmark.controller;

import com.anywhereworks.bookmark.dto.BookmarkDto;
import com.anywhereworks.bookmark.entity.Bookmark;
import com.anywhereworks.bookmark.service.impl.BookmarkServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/bookmarks")
public class BookmarkController {

  private BookmarkServiceImpl bookmarkService;

  public BookmarkController(BookmarkServiceImpl bookmarkService) {
    this.bookmarkService = bookmarkService;
  }

  @PostMapping
  public ResponseEntity<Bookmark> createBookmark(BookmarkDto bookmarkDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.createBookmark(bookmarkDto));
  }

  @PutMapping("/{bookmarkId}")
  public ResponseEntity<Bookmark> updateBookmark(@PathVariable Long bookmarkId, @RequestBody BookmarkDto bookmarkDto) {
    return ResponseEntity.ok(bookmarkService.updateBookmark(bookmarkDto, bookmarkId));
  }

  @DeleteMapping("/{bookmarkId}")
  public ResponseEntity<Void> deleteBookmark(@PathVariable Long bookmarkId) {
    bookmarkService.deleteBookmarkById(bookmarkId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Page<Bookmark>> getAllBookMarks(@RequestParam(required = false) String title,
                                                        @RequestParam(required = false) String description,
                                                        @RequestParam(required = false) LocalDate fromDate,
                                                        @RequestParam(required = false) LocalDate toDate,
                                                        @RequestParam(required = false) Pageable pag) {
    return ResponseEntity.ok(bookmarkService.fetchAllBookmarks(title, description, fromDate, toDate, pag));
  }

}
