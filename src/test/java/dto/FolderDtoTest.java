package dto;

import com.anywhereworks.bookmark.dto.FolderDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FolderDtoTest {

  @Test
  void testGetterAndSetter() {
    FolderDto folderDto = new FolderDto();
    folderDto.setName("Work Documents");
    folderDto.setFolderId("12345");

    assertEquals("Work Documents", folderDto.getName());
    assertEquals("12345", folderDto.getFolderId());
  }

  @Test
  void testAllArgsConstructor() {
    FolderDto folderDto = new FolderDto("Personal Files", "67890");

    assertEquals("Personal Files", folderDto.getName());
    assertEquals("67890", folderDto.getFolderId());
  }

  @Test
  void testNoArgsConstructor() {
    FolderDto folderDto = new FolderDto();
    assertNull(folderDto.getName());
    assertNull(folderDto.getFolderId());
  }

  @Test
  void testBuilder() {
    FolderDto folderDto = FolderDto.builder()
            .name("Projects")
            .folderId("A1B2C3")
            .build();

    assertEquals("Projects", folderDto.getName());
    assertEquals("A1B2C3", folderDto.getFolderId());
  }
}
