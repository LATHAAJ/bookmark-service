package com.anywhereworks.bookmark.mapper;

import com.anywhereworks.bookmark.dto.FolderDto;
import com.anywhereworks.bookmark.entity.FolderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FolderMapper {

  @Mapping(target = "id", ignore = true)
  FolderEntity toEntity(FolderDto folderDto);

  @Mapping(target = "id", ignore = true)
  void updateFromDto(FolderDto folderDto, @MappingTarget FolderEntity folder);
}
