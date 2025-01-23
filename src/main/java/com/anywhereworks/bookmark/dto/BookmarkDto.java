package com.anywhereworks.bookmark.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkDto {
  private String title;

  private String url;

  private String description;

  private LocalDateTime createdAt;
}
