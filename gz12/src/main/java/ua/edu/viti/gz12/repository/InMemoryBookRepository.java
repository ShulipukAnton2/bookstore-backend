package ua.edu.viti.gz12.repository;

import org.springframework.stereotype.Repository;
import ua.edu.viti.gz12.domain.Book;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryBookRepository implements BookRepository {
    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public InMemoryBookRepository() {
        save(new Book(null, "Тигролови", "Іван Багряний", new BigDecimal("320.00"), 1944,
                "Пригоди", "/covers/tigrolovy.jpg", "Пригодницький роман про свободу і гідність."));
        save(new Book(null, "Лісова пісня", "Леся Українка", new BigDecimal("250.00"), 1911,
                "Драма", "/covers/lisova-pisnia.jpg", "Драма-феєрія про світ людини й природи."));
        save(new Book(null, "Кобзар", "Тарас Шевченко", new BigDecimal("400.00"), 1840,
                "Поезія", "/covers/kobzar.jpg", "Збірка поетичних творів."));
    }

    @Override public List<Book> findAll() { return List.copyOf(storage.values()); }
    @Override public List<Book> findByGenre(String genre) {
        return storage.values().stream().filter(b -> b.getGenre() != null && genre.equalsIgnoreCase(b.getGenre())).toList();
    }
    @Override public Optional<Book> findById(Long id) { return Optional.ofNullable(storage.get(id)); }
    @Override public Book save(Book book) {
        if (book.getId() == null) book.setId(sequence.incrementAndGet());
        storage.put(book.getId(), book);
        return book;
    }
    @Override public boolean deleteById(Long id) { return storage.remove(id) != null; }
}
