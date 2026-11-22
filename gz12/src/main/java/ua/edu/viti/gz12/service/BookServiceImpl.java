package ua.edu.viti.gz12.service;

import org.springframework.stereotype.Service;
import ua.edu.viti.gz12.domain.Book;
import ua.edu.viti.gz12.repository.BookRepository;
import ua.edu.viti.gz12.service.mapper.BookMapper;
import ua.edu.viti.gz12.web.dto.*;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final BookMapper mapper;
    public BookServiceImpl(BookRepository repository, BookMapper mapper) { this.repository = repository; this.mapper = mapper; }

    @Override public List<BookCardDto> findAll(String genre) {
        List<Book> books = genre == null || genre.isBlank() ? repository.findAll() : repository.findByGenre(genre);
        return books.stream().map(mapper::toCard).toList();
    }
    @Override public BookDetailDto findById(Long id) {
        return repository.findById(id).map(mapper::toDetail).orElseThrow(() -> new BookNotFoundException(id));
    }
    @Override public BookDetailDto create(BookRequest request) {
        Book book = mapper.toDomain(request);
        book.setId(null);
        return mapper.toDetail(repository.save(book));
    }
    @Override public BookDetailDto update(Long id, BookRequest request) {
        repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        Book book = mapper.toDomain(request);
        book.setId(id);
        return mapper.toDetail(repository.save(book));
    }
    @Override public void delete(Long id) {
        if (!repository.deleteById(id)) throw new BookNotFoundException(id);
    }
}
