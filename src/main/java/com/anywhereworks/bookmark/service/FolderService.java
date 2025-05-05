package com.anywhereworks.bookmark.service;

import com.anywhereworks.bookmark.dto.FolderDto;
import com.anywhereworks.bookmark.entity.Folder;

import java.util.List;

public interface FolderService {
  List<Folder> fetchAllFolders();

  Folder fetchFolderById(Long folderId);

  Folder createFolder(FolderDto folderDto);

  Folder updateFolder(FolderDto folderDto, Long folderId);

  void deleteFolderById(Long folderId);
}
