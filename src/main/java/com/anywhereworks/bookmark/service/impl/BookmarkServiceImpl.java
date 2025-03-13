package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.dto.BookmarkDto;
import com.anywhereworks.bookmark.entity.Bookmark;
import com.anywhereworks.bookmark.entity.Folder;
import com.anywhereworks.bookmark.mapper.BookmarkMapper;
import com.anywhereworks.bookmark.repository.BookmarkRepository;
import com.anywhereworks.bookmark.service.BookmarkService;
import com.anywhereworks.bookmark.service.FolderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final FolderService folderService;
  private final BookmarkMapper bookmarkMapper;

  private final BookmarkSpecification bookmarkSpecification;

  public BookmarkServiceImpl(BookmarkRepository bookmarkRepository, FolderService folderService, BookmarkMapper bookmarkMapper, BookmarkSpecification bookmarkSpecification) {
    this.bookmarkRepository = bookmarkRepository;
    this.folderService = folderService;
    this.bookmarkMapper = bookmarkMapper;
    this.bookmarkSpecification= bookmarkSpecification;
  }
  public Page<Bookmark> fetchAllBookmarks(String title, String description, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
    return bookmarkRepository.findAll(bookmarkSpecification.filterBy(title, description, fromDate, toDate), pageable);
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
  @Override
  public List<Bookmark> fetchAllBookmarks() {
    return bookmarkRepository.findAll();
  }
}
