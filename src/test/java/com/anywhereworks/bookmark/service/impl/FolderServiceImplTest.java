package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.dto.FolderDto;
import com.anywhereworks.bookmark.entity.Folder;
import com.anywhereworks.bookmark.mapper.FolderMapper;
import com.anywhereworks.bookmark.repository.FolderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class FolderServiceImplTest {
  @Test
  public void fetchAll_folderslist() {
    FolderServiceImpl folderService = new FolderServiceImpl();
    FolderRepository folderRepository = mock(FolderRepository.class);
    ReflectionTestUtils.setField(folderService, "folderRepository", folderRepository);

    List<Folder> expectedFolders = Arrays.asList(
            Folder.builder().id("1").name("Folder1").build(),
            Folder.builder().id("2").name("Folder2").build()
    );
    when(folderRepository.findAll()).thenReturn(expectedFolders);
    List<Folder> actualFolders = folderService.fetchAllFolders();
    assertEquals(expectedFolders, actualFolders);
  }
  @Test
  public void fetchFolderByValidId() {
    Long folderId = 1L;
    Folder expectedFolder = Folder.builder()
            .id("1")
            .name("Test Folder")
            .folderId("1")
            .build();

    FolderServiceImpl folderService = new FolderServiceImpl();
    FolderRepository folderRepository = mock(FolderRepository.class);
    ReflectionTestUtils.setField(folderService, "folderRepository", folderRepository);
    when(folderRepository.findById(folderId)).thenReturn(Optional.of(expectedFolder));
    Folder actualFolder = folderService.fetchFolderById(folderId);
    assertNotNull(actualFolder);
    assertEquals(expectedFolder.getId(), actualFolder.getId());
    assertEquals(expectedFolder.getName(), actualFolder.getName());
    assertEquals(expectedFolder.getFolderId(), actualFolder.getFolderId());
  }
  @Test
  public void createFolderWithValid_dto() {
    // Given
    FolderServiceImpl folderService = new FolderServiceImpl();
    FolderRepository folderRepository = mock(FolderRepository.class);
    FolderMapper folderMapper = mock(FolderMapper.class);
    ReflectionTestUtils.setField(folderService, "folderRepository", folderRepository);
    ReflectionTestUtils.setField(folderService, "folderMapper", folderMapper);

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
  }

  @Test
  public void deleteExisting_Folder() {
    Long folderId = 1L;
    Folder mockFolder = new Folder();

    FolderServiceImpl folderService = new FolderServiceImpl();
    FolderRepository folderRepository = mock(FolderRepository.class);
    ReflectionTestUtils.setField(folderService, "folderRepository", folderRepository);

    when(folderRepository.findById(folderId)).thenReturn(Optional.of(mockFolder));
    doNothing().when(folderRepository).delete(mockFolder);

    // When
    folderService.deleteFolderById(folderId);

    // Then
    verify(folderRepository).findById(folderId);
    verify(folderRepository).delete(mockFolder);
  }


}
