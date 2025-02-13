package com.anywhereworks.bookmark.mapper;

import com.anywhereworks.bookmark.dto.BookmarkDto;
import com.anywhereworks.bookmark.entity.Bookmark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "folder", ignore = true)
  Bookmark toEntity(BookmarkDto bookmarkDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "folder", ignore = true)
  void updateFromDto(BookmarkDto bookmarkDto, @MappingTarget Bookmark bookmark);

}
