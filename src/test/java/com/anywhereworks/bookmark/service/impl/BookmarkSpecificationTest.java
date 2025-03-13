package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.entity.Bookmark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookmarkSpecificationTest {

  private BookmarkSpecification bookmarkSpecification;
  private Root<Bookmark> root;
  private CriteriaQuery<?> query;
  private CriteriaBuilder criteriaBuilder;
  private Path<String> titlePath;
  private Path<String> descriptionPath;
  private Path<LocalDate> creationDatePath;
  private Predicate predicateMock;

  @BeforeEach
  void setUp() {
    bookmarkSpecification = new BookmarkSpecification();
    root = mock(Root.class);
    query = mock(CriteriaQuery.class);
    criteriaBuilder = mock(CriteriaBuilder.class);
    titlePath = mock(Path.class);
    descriptionPath = mock(Path.class);
    creationDatePath = mock(Path.class);
    predicateMock = mock(Predicate.class);
    when(root.get("title")).thenReturn((Path) titlePath);
    when(root.get("description")).thenReturn((Path) descriptionPath);
    when(root.get("creationDate")).thenReturn((Path) creationDatePath);
    when(criteriaBuilder.like(any(), (Expression<String>) any())).thenReturn(predicateMock);
    when(criteriaBuilder.lower(any())).thenReturn(titlePath); // Lowercase conversion
    when(criteriaBuilder.and(any(Predicate[].class))).thenReturn(predicateMock);
  }

  @Test
  void testFilterByTitle() {
    Specification<Bookmark> spec = bookmarkSpecification.filterBy("java", null, null, null);
    Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
    assertNotNull(predicate);
    verify(criteriaBuilder).like(criteriaBuilder.lower(titlePath), "%java%");
  }

  @Test
  void testFilterByDescription() {
    Specification<Bookmark> spec = bookmarkSpecification.filterBy(null, "programming", null, null);
    Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
    assertNotNull(predicate);
    verify(criteriaBuilder).like(criteriaBuilder.lower(descriptionPath), "%programming%");
  }

  @Test
  void testFilterByFromDate() {
    LocalDate fromDate = LocalDate.of(2024, 1, 1);
    Specification<Bookmark> spec = bookmarkSpecification.filterBy(null, null, fromDate, null);
    Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
    assertNotNull(predicate);
    verify(criteriaBuilder).greaterThanOrEqualTo(creationDatePath, fromDate);
  }

  @Test
  void testFilterByToDate() {
    LocalDate toDate = LocalDate.of(2024, 12, 31);
    Specification<Bookmark> spec = bookmarkSpecification.filterBy(null, null, null, toDate);
    Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
    assertNotNull(predicate);
    verify(criteriaBuilder).lessThanOrEqualTo(creationDatePath, toDate);
  }

  @Test
  void testFilterByMultipleConditions() {
    LocalDate fromDate = LocalDate.of(2024, 1, 1);
    LocalDate toDate = LocalDate.of(2024, 12, 31);
    Specification<Bookmark> spec = bookmarkSpecification.filterBy("java", "programming", fromDate, toDate);
    Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
    assertNotNull(predicate);
    verify(criteriaBuilder).like(criteriaBuilder.lower(titlePath), "%java%");
    verify(criteriaBuilder).like(criteriaBuilder.lower(descriptionPath), "%programming%");
    verify(criteriaBuilder).greaterThanOrEqualTo(creationDatePath, fromDate);
    verify(criteriaBuilder).lessThanOrEqualTo(creationDatePath, toDate);
  }
}