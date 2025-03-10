package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.dto.FolderDto;
import com.anywhereworks.bookmark.entity.Folder;
import com.anywhereworks.bookmark.mapper.FolderMapper;
import com.anywhereworks.bookmark.repository.FolderRepository;
import com.anywhereworks.bookmark.service.FolderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImpl implements FolderService {

  private final FolderRepository folderRepository;
  private final FolderMapper folderMapper;

  public FolderServiceImpl(FolderRepository folderRepository, FolderMapper folderMapper) {
    this.folderRepository = folderRepository;
    this.folderMapper = folderMapper;
  }

  @Override
  public List<Folder> fetchAllFolders() {
    return folderRepository.findAll();
  }

  @Override
  public Folder fetchFolderById(Long folderId) {
    return folderRepository.findById(folderId)
            .orElseThrow(() -> new EntityNotFoundException("Folder not found with id: " + folderId));
  }

  @Override
  public Folder createFolder(FolderDto folderDto) {
    return folderRepository.save(folderMapper.toEntity(folderDto));
  }

  @Override
  public Folder updateFolder(FolderDto folderDto, Long folderId) {
    Folder folder = this.fetchFolderById(folderId);
    folderMapper.updateFromDto(folderDto, folder);
    return folderRepository.save(folder);
  }

  @Override
  public void deleteFolderById(Long folderId) {
    folderRepository.delete(this.fetchFolderById(folderId));
  }
}
