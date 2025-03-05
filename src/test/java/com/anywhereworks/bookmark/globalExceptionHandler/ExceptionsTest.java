package com.anywhereworks.bookmark.globalExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExceptionsTest {
  @Test
  public void handle_entityNotFound() {
    Exceptions exceptionHandler = new Exceptions();
    String errorMessage = "Entity not found";
    EntityNotFoundException exception = new EntityNotFoundException(errorMessage);
    ResponseEntity<String> response = exceptionHandler.handleEntityNotFound(exception);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(errorMessage, response.getBody());
  }
  @Test
  public void single_validation_error() {
    Exceptions exceptionHandler = new Exceptions();
    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("object", "email", "Email is invalid");

    when(ex.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
    ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidationExceptions(ex);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(1, response.getBody().size());
    assertEquals("Email is invalid", response.getBody().get("email"));
  }

  @Test
  public void generic_exception_returns_500_status() {
    Exceptions exceptionHandler = new Exceptions();
    Exception testException = new RuntimeException("test error");

    ResponseEntity<String> response = exceptionHandler.handleGenericException(testException);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An unexpected error occurred: test error", response.getBody());
  }

}
