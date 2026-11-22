package ua.edu.viti.gz12.service;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) { super("Book with id " + id + " was not found"); }
}
