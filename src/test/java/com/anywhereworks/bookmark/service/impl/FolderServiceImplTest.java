package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.dto.FolderDto;
import com.anywhereworks.bookmark.entity.Folder;
import com.anywhereworks.bookmark.mapper.FolderMapper;
import com.anywhereworks.bookmark.repository.FolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FolderServiceImplTest {

  private FolderServiceImpl folderService;

  @Mock
  private FolderRepository folderRepository;

  @Mock
  private FolderMapper folderMapper;

  @BeforeEach
  void setUp() {
    folderService = new FolderServiceImpl(folderRepository, folderMapper);
  }

  @Test
  void testFetchAllFolders() {
    List<Folder> expectedFolders = List.of(
            Folder.builder().id("1").name("Folder1").build(),
            Folder.builder().id("2").name("Folder2").build()
    );

    when(folderRepository.findAll()).thenReturn(expectedFolders);

    List<Folder> actualFolders = folderService.fetchAllFolders();

    assertEquals(expectedFolders, actualFolders);
    verify(folderRepository, times(1)).findAll();
  }

  @Test
  void testFetchFolderByValidId() {
    Long folderId = 1L;
    Folder expectedFolder = Folder.builder()
            .id("1")
            .name("Test Folder")
            .folderId("1")
            .build();

    when(folderRepository.findById(folderId)).thenReturn(Optional.of(expectedFolder));

    Folder actualFolder = folderService.fetchFolderById(folderId);

    assertNotNull(actualFolder);
    assertEquals(expectedFolder, actualFolder);
    verify(folderRepository, times(1)).findById(folderId);
  }

  @Test
  void testFetchFolderById_NotFound() {
    Long folderId = 1L;

    when(folderRepository.findById(folderId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> folderService.fetchFolderById(folderId));
    verify(folderRepository, times(1)).findById(folderId);
  }

  @Test
  void testCreateFolder() {
    FolderDto folderDto = FolderDto.builder()
            .name("Test Folder")
            .folderId("folder123")
            .build();

    Folder expectedFolder = Folder.builder()
            .id("1")
            .name("Test Folder")
            .folderId("folder123")
            .build();

    when(folderMapper.toEntity(folderDto)).thenReturn(expectedFolder);
    when(folderRepository.save(expectedFolder)).thenReturn(expectedFolder);

    Folder actualFolder = folderService.createFolder(folderDto);

    assertEquals(expectedFolder, actualFolder);
    verify(folderRepository, times(1)).save(expectedFolder);
  }

  @Test
  void testDeleteExistingFolder() {
    Long folderId = 1L;
    Folder mockFolder = new Folder();

    when(folderRepository.findById(folderId)).thenReturn(Optional.of(mockFolder));
    doNothing().when(folderRepository).delete(mockFolder);

    assertDoesNotThrow(() -> folderService.deleteFolderById(folderId));

    verify(folderRepository, times(1)).findById(folderId);
    verify(folderRepository, times(1)).delete(mockFolder);
  }
}
