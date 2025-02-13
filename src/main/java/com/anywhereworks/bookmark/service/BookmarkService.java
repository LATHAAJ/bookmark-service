package com.anywhereworks.bookmark.service;

import com.anywhereworks.bookmark.dto.BookmarkDto;
import com.anywhereworks.bookmark.entity.Bookmark;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BookmarkService {

  List<Bookmark> fetchAllBookmarks();

  Bookmark fetchBookmarkById(Long bookmarkId);

  Bookmark createBookmark(BookmarkDto bookmarkDto);

  Bookmark updateBookmark(BookmarkDto bookmarkDto, Long bookmarkId);

  Bookmark addBookmarkToFolder(Long bookmarkId, Long folderId);

  void deleteBookmarkById(Long bookmarkId);
}
