package ua.edu.viti.gz12.service.mapper;

import org.springframework.stereotype.Component;
import ua.edu.viti.gz12.domain.Book;
import ua.edu.viti.gz12.web.dto.*;

@Component
public class BookMapper {
    public Book toDomain(BookRequest request) {
        return new Book(null, request.title(), request.author(), request.price(), request.year(),
                request.genre(), request.coverUrl(), request.description());
    }
    public BookCardDto toCard(Book book) {
        return new BookCardDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(),
                book.getYear(), book.getGenre(), book.getCoverUrl());
    }
    public BookDetailDto toDetail(Book book) {
        return new BookDetailDto(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(),
                book.getYear(), book.getGenre(), book.getCoverUrl(), book.getDescription());
    }
}
