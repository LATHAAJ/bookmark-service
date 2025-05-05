package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.entity.Bookmark;
import com.anywhereworks.bookmark.repository.BookmarkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookmarkServiceImplTest {

  @Mock
  private BookmarkRepository bookmarkRepository;

  @Mock
  private BookmarkSpecification bookmarkSpecification;

  @InjectMocks
  private BookmarkServiceImpl bookmarkService;

  @Test
  void testFetchAllBookmarks() {
    List<Bookmark> bookmarkList = List.of(
            Bookmark.builder().id(1L).title("Test1").url("http://test1.com").build(),
            Bookmark.builder().id(2L).title("Test2").url("http://test2.com").build()
    );

    Pageable pageable = Pageable.unpaged();
    Specification<Bookmark> mockSpec = (root, query, cb) -> cb.conjunction();

    when(bookmarkSpecification.filterBy("", "", null, null)).thenReturn(mockSpec);

    Page<Bookmark> expectedPage = new PageImpl<>(bookmarkList);
    when(bookmarkRepository.findAll(mockSpec, pageable)).thenReturn(expectedPage);

    Page<Bookmark> result = bookmarkService.fetchAllBookmarks("", "", null, null, pageable);

    assertEquals(expectedPage, result);
    verify(bookmarkRepository, times(1)).findAll(mockSpec, pageable);
  }

  @Test
  void testFetchBookmarkById_Success() {
    Long bookmarkId = 1L;
    Bookmark expectedBookmark = Bookmark.builder()
            .id(bookmarkId)
            .title("Test Bookmark")
            .url("http://test.com")
            .build();

    when(bookmarkRepository.findById(bookmarkId)).thenReturn(Optional.of(expectedBookmark));

    Bookmark actualBookmark = bookmarkService.fetchBookmarkById(bookmarkId);

    assertNotNull(actualBookmark);
    assertEquals(expectedBookmark, actualBookmark);
    verify(bookmarkRepository, times(1)).findById(bookmarkId);
  }

  @Test
  void testFetchBookmarkById_NotFound() {
    Long bookmarkId = 1L;

    when(bookmarkRepository.findById(bookmarkId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookmarkService.fetchBookmarkById(bookmarkId));
    verify(bookmarkRepository, times(1)).findById(bookmarkId);
  }

  @Test
  void testDeleteBookmarkById() {
    Long bookmarkId = 1L;

    doNothing().when(bookmarkRepository).deleteById(bookmarkId);

    assertDoesNotThrow(() -> bookmarkService.deleteBookmarkById(bookmarkId));

    verify(bookmarkRepository, times(1)).deleteById(bookmarkId);
  }
}
