package com.anywhereworks.bookmark.service;

import com.anywhereworks.bookmark.dto.BookmarkDto;
import com.anywhereworks.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BookmarkService {

  Page<Bookmark> fetchAllBookmarks(String title, String description, LocalDate fromDate, LocalDate toDate, Pageable pageable);

  Bookmark fetchBookmarkById(Long bookmarkId);

  Bookmark createBookmark(BookmarkDto bookmarkDto);

  Bookmark updateBookmark(BookmarkDto bookmarkDto, Long bookmarkId);

  Bookmark addBookmarkToFolder(Long bookmarkId, Long folderId);

  void deleteBookmarkById(Long bookmarkId);
}
