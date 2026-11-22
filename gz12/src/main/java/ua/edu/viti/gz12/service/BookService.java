package ua.edu.viti.gz12.service;

import ua.edu.viti.gz12.web.dto.*;
import java.util.List;

public interface BookService {
    List<BookCardDto> findAll(String genre);
    BookDetailDto findById(Long id);
    BookDetailDto create(BookRequest request);
    BookDetailDto update(Long id, BookRequest request);
    void delete(Long id);
}
