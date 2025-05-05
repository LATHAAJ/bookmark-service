package com.anywhereworks.bookmark.service.impl;

import com.anywhereworks.bookmark.entity.Bookmark;
import jakarta.persistence.criteria.ParameterExpression;
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
        ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class, "titlePattern");
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), titleParam));
      }

      if (description != null && !description.isEmpty()) {
        ParameterExpression<String> descParam = criteriaBuilder.parameter(String.class, "descriptionPattern");
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), descParam));
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