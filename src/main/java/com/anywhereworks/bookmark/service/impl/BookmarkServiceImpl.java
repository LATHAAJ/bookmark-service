package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.dto.BookmarkDto;
import com.anywhereworks.bookmark.entity.Bookmark;
import com.anywhereworks.bookmark.entity.Folder;
import com.anywhereworks.bookmark.mapper.BookmarkMapper;
import com.anywhereworks.bookmark.repository.BookmarkRepository;
import com.anywhereworks.bookmark.service.BookmarkService;
import com.anywhereworks.bookmark.service.FolderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {

  @Autowired
  private BookmarkRepository bookmarkRepository;
  @Autowired
  private FolderService folderService;
  @Autowired
  private BookmarkMapper bookmarkMapper;

  @Override
  public List<Bookmark> fetchAllBookmarks() {
    return bookmarkRepository.findAll();
  }

  @Override
  public Bookmark fetchBookmarkById(Long bookmarkId) {
    return bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new EntityNotFoundException("Bookmark not found with id: " + bookmarkId));
  }

  @Override
  public Bookmark createBookmark(BookmarkDto bookmarkDto) {
    Bookmark bookmark = bookmarkMapper.toEntity(bookmarkDto);
    return bookmarkRepository.save(bookmark);
  }

  @Override
  public Bookmark updateBookmark(BookmarkDto bookmarkDto, Long bookmarkId) {
    Bookmark bookmark = this.fetchBookmarkById(bookmarkId);
    bookmarkMapper.updateFromDto(bookmarkDto, bookmark);
    return bookmarkRepository.save(bookmark);
  }

  @Override
  public Bookmark addBookmarkToFolder(Long bookmarkId, Long folderId) {
    Bookmark bookmark = this.fetchBookmarkById(bookmarkId);
    Folder folder = folderService.fetchFolderById(folderId);
    bookmark.setFolder(folder);
    return bookmarkRepository.save(bookmark);
  }

  @Override
  public void deleteBookmarkById(Long bookmarkId) {
    bookmarkRepository.deleteById(bookmarkId);
  }

}
