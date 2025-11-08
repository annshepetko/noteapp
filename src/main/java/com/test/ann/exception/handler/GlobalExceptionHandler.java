package com.test.ann.exception.handler;

import com.test.ann.exception.NoteNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for the application.
 * <p>
 * Handles common exceptions thrown by controllers and provides
 * standardized responses for API clients.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Represents the body of an exception response.
     * Contains a message, HTTP status, and timestamp.
     */
    @Getter
    @Setter
    public static class ExceptionMessageBody {

        private String message;
        private HttpStatus httpStatus;
        private LocalDateTime timestamp;

        /**
         * Constructs a new exception message body.
         *
         * @param message    the exception message
         * @param httpStatus the HTTP status associated with the exception
         * @param timestamp  the time when the exception occurred
         */
        public ExceptionMessageBody(String message, HttpStatus httpStatus, LocalDateTime timestamp) {
            this.message = message;
            this.httpStatus = httpStatus;
            this.timestamp = timestamp;
        }
    }

    /**
     * Handles validation errors thrown when method arguments are invalid.
     *
     * @param ex the {@link MethodArgumentNotValidException} exception
     * @return a {@link ResponseEntity} containing the list of validation errors and a BAD_REQUEST status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .toList();

        Map<String, Object> body = new HashMap<>();
        body.put("errors", errors);
        body.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Handles {@link NoteNotFoundException} thrown when a note is not found.
     *
     * @param ex the {@link NoteNotFoundException} exception
     * @return a {@link ResponseEntity} containing the exception details and a NOT_FOUND status
     */
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<ExceptionMessageBody> handleException(NoteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionMessageBody(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now()
                ));
    }
}
