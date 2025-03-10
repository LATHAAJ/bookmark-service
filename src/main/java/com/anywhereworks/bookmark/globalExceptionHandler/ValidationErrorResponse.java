package com.anywhereworks.bookmark.globalExceptionHandler;
import java.util.List;

public class ValidationErrorResponse {
  private List<FieldErrorDTO> errors;

  public ValidationErrorResponse(List<FieldErrorDTO> errors) {
    this.errors = errors;
  }

  public List<FieldErrorDTO> getErrors() {
    return errors;
  }

  public void setErrors(List<FieldErrorDTO> errors) {
    this.errors = errors;
  }
}
