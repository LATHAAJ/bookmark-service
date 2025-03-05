package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.entity.Bookmark;
import com.anywhereworks.bookmark.mapper.BookmarkMapper;
import com.anywhereworks.bookmark.repository.BookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BookmarkServiceImplTest {

  private BookmarkServiceImpl bookmarkService;

  @Mock
  private BookmarkRepository bookmarkRepository;

  @Mock
  private BookmarkMapper bookmarkMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    bookmarkService = new BookmarkServiceImpl();
  }
  @Test
  public void fetchAllBookmarks() {
    BookmarkRepository bookmarkRepository = mock(BookmarkRepository.class);
    BookmarkServiceImpl bookmarkService = new BookmarkServiceImpl();
    ReflectionTestUtils.setField(bookmarkService, "bookmarkRepository", bookmarkRepository);

    List<Bookmark> expectedBookmarks = Arrays.asList(
            Bookmark.builder().id(1L).title("Test1").url("http://test1.com").build(),
            Bookmark.builder().id(2L).title("Test2").url("http://test2.com").build()
    );

    when(bookmarkRepository.findAll()).thenReturn(expectedBookmarks);
    List<Bookmark> actualBookmarks = bookmarkService.fetchAllBookmarks();
    assertEquals(expectedBookmarks, actualBookmarks);
    verify(bookmarkRepository).findAll();
  }

  @Test
  public void fetchBookmarkById() {
    Long bookmarkId = 1L;
    Bookmark expectedBookmark = Bookmark.builder()
            .id(bookmarkId)
            .title("Test Bookmark")
            .url("http://test.com")
            .build();
    BookmarkRepository bookmarkRepository = mock(BookmarkRepository.class);
    when(bookmarkRepository.findById(bookmarkId)).thenReturn(Optional.of(expectedBookmark));
    BookmarkServiceImpl bookmarkService = new BookmarkServiceImpl();
    ReflectionTestUtils.setField(bookmarkService, "bookmarkRepository", bookmarkRepository);
    Bookmark actualBookmark = bookmarkService.fetchBookmarkById(bookmarkId);
    assertNotNull(actualBookmark);
    assertEquals(expectedBookmark.getId(), actualBookmark.getId());
    assertEquals(expectedBookmark.getTitle(), actualBookmark.getTitle());
    assertEquals(expectedBookmark.getUrl(), actualBookmark.getUrl());
    verify(bookmarkRepository).findById(bookmarkId);
  }

  @Test
  public void deleteExistingBookmarkByValid_id() {
    Long bookmarkId = 1L;
    BookmarkRepository bookmarkRepository = mock(BookmarkRepository.class);
    BookmarkServiceImpl bookmarkService = new BookmarkServiceImpl();
    ReflectionTestUtils.setField(bookmarkService, "bookmarkRepository", bookmarkRepository);

    bookmarkService.deleteBookmarkById(bookmarkId);

    verify(bookmarkRepository, times(1)).deleteById(bookmarkId);
  }

}