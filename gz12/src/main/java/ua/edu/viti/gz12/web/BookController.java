package ua.edu.viti.gz12.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.gz12.service.BookService;
import ua.edu.viti.gz12.web.dto.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping public List<BookCardDto> list(@RequestParam(required = false) String genre) { return bookService.findAll(genre); }
    @GetMapping("/{id}") public BookDetailDto get(@PathVariable Long id) { return bookService.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public BookDetailDto create(@RequestBody BookRequest request) { return bookService.create(request); }
    @PutMapping("/{id}") public BookDetailDto update(@PathVariable Long id, @RequestBody BookRequest request) {
        return bookService.update(id, request);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
