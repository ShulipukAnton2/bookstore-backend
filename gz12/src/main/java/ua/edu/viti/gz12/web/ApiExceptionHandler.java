package ua.edu.viti.gz12.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.gz12.service.BookNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(BookNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }
    public record ErrorResponse(String message) {}
}
