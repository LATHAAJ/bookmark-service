package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.entity.Bookmark;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookmarkSpecification {
  public Specification<Bookmark> filterBy(String title, String description, LocalDate fromDate, LocalDate toDate) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (title != null && !title.isEmpty()) {
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
      }
      if (description != null && !description.isEmpty()) {
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
      }
      if (fromDate != null) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), fromDate));
      }
      if (toDate != null) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), toDate));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}