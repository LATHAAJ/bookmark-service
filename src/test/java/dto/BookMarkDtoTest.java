package dto;
import com.anywhereworks.bookmark.dto.BookmarkDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookmarkDtoTest {

  @Test
  void testBookmarkDtoBuilderAndAccessors() {
    String title = "My Bookmark";
    String url = "https://example.com";
    String description = "A useful link.";
    LocalDateTime createdAt = LocalDateTime.now();

    BookmarkDto bookmarkDto = BookmarkDto.builder()
            .title(title)
            .url(url)
            .description(description)
            .createdAt(createdAt)
            .build();

    assertNotNull(bookmarkDto);
    assertEquals(title, bookmarkDto.getTitle());
    assertEquals(url, bookmarkDto.getUrl());
    assertEquals(description, bookmarkDto.getDescription());
    assertEquals(createdAt, bookmarkDto.getCreatedAt());
  }
  @Test
  void testBookmarkDtoAllArgsConstructor() {
    String title = "Another Bookmark";
    String url = "https://another.com";
    String description = "Another description.";
    LocalDateTime createdAt = LocalDateTime.now();

    BookmarkDto bookmarkDto = new BookmarkDto(title, url, description, createdAt);

    assertNotNull(bookmarkDto);
    assertEquals(title, bookmarkDto.getTitle());
    assertEquals(url, bookmarkDto.getUrl());
    assertEquals(description, bookmarkDto.getDescription());
    assertEquals(createdAt, bookmarkDto.getCreatedAt());
  }
}