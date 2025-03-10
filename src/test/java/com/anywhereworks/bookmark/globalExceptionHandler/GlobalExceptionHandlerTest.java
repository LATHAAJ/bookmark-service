package com.anywhereworks.bookmark.globalExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {
  @Test
  public void handle_entityNotFound() {
    GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
    String errorMessage = "Entity not found";
    EntityNotFoundException exception = new EntityNotFoundException(errorMessage);
    ResponseEntity<String> response = exceptionHandler.handleEntityNotFound(exception);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(errorMessage, response.getBody());
  }
  // Method correctly transforms MethodArgumentNotValidException into ValidationErrorResponse
  @Test
  public void test_transforms_method_argument_not_valid_exception_into_validation_error_response() {
    // Given
    GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = mock(FieldError.class);
    List<ObjectError> errors = List.of(fieldError);

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getAllErrors()).thenReturn(errors);
    when(fieldError.getField()).thenReturn("username");
    when(fieldError.getDefaultMessage()).thenReturn("must not be blank");

    // When
    ResponseEntity<ValidationErrorResponse> response = exceptionHandler.handleValidationExceptions(exception);

    // Then
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    ValidationErrorResponse errorResponse = response.getBody();
    assertNotNull(errorResponse);

    List<FieldErrorDTO> fieldErrors = errorResponse.getErrors();
    assertEquals(1, fieldErrors.size());

    FieldErrorDTO fieldErrorDTO = fieldErrors.get(0);
    assertEquals("username", fieldErrorDTO.getField());
    assertEquals("must not be blank", fieldErrorDTO.getMessage());
  }

  @Test
  public void generic_exception_returns_500_status() {
    GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
    Exception testException = new RuntimeException("test error");

    ResponseEntity<String> response = exceptionHandler.handleGenericException(testException);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An unexpected error occurred: test error", response.getBody());
  }

}
