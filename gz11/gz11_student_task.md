# Самостійна робота до заняття 1.1 — власний проєкт (лише backend)

## Мета

Створити **власний проєкт** інтернет-магазину за зразком заняття та реалізувати
**два ендпоінти читання** (перелік і один елемент) — аналогічно до еталона, але на
**власній предметній області** та у **власному git-репозиторії**.

На цьому етапі — **лише backend**, без інтерфейсу користувача (UI буде окремою
самостійною роботою пізніше).

## Умови

- Ви робите **свій окремий проєкт**, а не копіюєте репозиторій викладача. Еталон
  (`amazonua`) — це приклад, на який ви орієнтуєтесь, а не основа вашого коду.
- Предметна область — **ваша власна**. У цьому гайді наскрізний приклад — **книгарня**
  (сутність `Book`). Ви можете взяти книгарню або будь-який інший магазин (фільми,
  ігри, рецепти тощо) — головне, щоб поля відрізнялися від товарів еталона.
- Стек той самий: **Spring Boot 3.4.x, Java 21, Maven**.
- Архітектура та принципи — ті самі, що й на занятті (шари + інтерфейси, Clean Code,
  SOLID). Дивіться README заняття як довідник.

---

## Що має вийти (огляд)

Діаграма класів вашого проєкту на прикладі книгарні:

![Діаграма класів книгарні](./media-student/class-diagram.svg)

Два ендпоінти читання:

![Два ендпоінти читання](./media-student/two-endpoints.svg)

Далі — покрокова інструкція, як це зробити.

---

## Крок 1. Створити власний проєкт

![Створення проєкту через Spring Initializr](./media-student/project-setup.svg)

### 1.1. Згенерувати проєкт

Скористайтеся офіційним генератором **Spring Initializr**. Є два рівноцінні способи:

**Спосіб А — веб-сайт** <https://start.spring.io>:
- **Project:** Maven
- **Language:** Java
- **Spring Boot:** версія гілки 3.4.x
- **Group:** `com.ua`
- **Artifact:** `bookstore` (або назва вашого магазину)
- **Java:** 21
- **Dependencies:** `Spring Web` (за бажанням `Lombok`)
- Натисніть **Generate** — завантажиться архів. Розпакуйте й відкрийте в IntelliJ IDEA.

**Спосіб Б — у IntelliJ IDEA:** `File → New → Project… → Spring Boot` (використовує той
самий Initializr). Виберіть ті самі параметри.

### 1.2. Створити свій git-репозиторій

У корені проєкту:
```bash
git init
git add .
git commit -m "initial: порожній Spring Boot проєкт"
```
Це **ваш** репозиторій і **ваша** історія. Ви нарощуватимете в ньому свій проєкт від
заняття до заняття.

### 1.3. Перевірити, що проєкт запускається

Запустіть клас `*Application` (напр. `BookstoreApplication`). У консолі має з'явитися
рядок `Started ... Application`. Поки що жодних ендпоінтів немає — це нормально.

> Якщо порт 8080 зайнятий — див. розділ «Запуск і перевірка» у README заняття
> (команди звільнення порту).

---

## Крок 2. Спланувати структуру пакетів

Створіть пакети за тією самою багатошаровою схемою, що й на занятті. Приклад для книгарні:

![Структура пакетів](./media-student/package-structure.svg)

Нагадування принципів (докладно — у README заняття, розділи 4.1–4.3):
- кожен шар — окремий пакет;
- на межах шарів — **інтерфейси** (`BookService`, `BookRepository`);
- доменний клас — без анотацій Spring;
- залежності — через конструктор.

---

## Крок 3. Створити доменну модель

Створіть клас `Book` у пакеті `domain`. Оберіть **свої** поля (відмінні від товарів
еталона). Приклад:

```java
package com.ua.bookstore.domain;

import java.math.BigDecimal;

public class Book {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer year;
    private String coverUrl;       // обкладинка (для картки)
    private String description;    // опис (для сторінки книги)
    private String genre;

    public Book() {
    }

    public Book(Long id, String title, String author, BigDecimal price,
                Integer year, String coverUrl, String description, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.year = year;
        this.coverUrl = coverUrl;
        this.description = description;
        this.genre = genre;
    }

    // гетери і сетери для всіх полів — згенеруйте в IDE
    // (Code → Generate → Getter and Setter)
}
```

> Поле `coverUrl` потрібне для картки у переліку, `description`/`genre` — для сторінки
> книги. Це аналог `imageUrl`/`description` з еталона, але під вашу область.

---

## Крок 4. Створити DTO

Назовні API повертає не доменний `Book`, а окремі DTO (`record`). Два DTO:

```java
// web/dto/BookCardDto.java — для переліку (мінімум полів)
package com.ua.bookstore.web.dto;

import java.math.BigDecimal;

public record BookCardDto(Long id, String title, String author,
                          BigDecimal price, String coverUrl) {
}
```

```java
// web/dto/BookDetailDto.java — для сторінки книги (повний набір)
package com.ua.bookstore.web.dto;

import java.math.BigDecimal;

public record BookDetailDto(Long id, String title, String author, BigDecimal price,
                            Integer year, String coverUrl, String description, String genre) {
}
```

---

## Крок 5. Створити репозиторій (інтерфейс + реалізація)

**Інтерфейс** — контракт сховища:
```java
// repository/BookRepository.java
package com.ua.bookstore.repository;

import com.ua.bookstore.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book save(Book book);
}
```

**Реалізація** в пам'яті:
```java
// repository/InMemoryBookRepository.java
package com.ua.bookstore.repository;

import com.ua.bookstore.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(sequence.incrementAndGet());
        }
        storage.put(book.getId(), book);
        return book;
    }
}
```

---

## Крок 6. Наповнити сховище початковими книгами

Оскільки ендпоінта створення ще немає, початкові книги додаються **в коді** —
окремим компонентом, що виконується під час запуску.

```java
// config/BookSeeder.java
package com.ua.bookstore.config;

import com.ua.bookstore.domain.Book;
import com.ua.bookstore.repository.BookRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BookSeeder implements ApplicationRunner {

    private final BookRepository repository;

    public BookSeeder(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) {
        repository.save(new Book(null, "Кобзар", "Тарас Шевченко",
                new BigDecimal("250.00"), 1840, "/covers/kobzar.jpg",
                "Збірка поетичних творів.", "Поезія"));
        // додайте ще 3–4 свої книги з власними полями
    }
}
```

> Саме так додаються дані, поки немає `POST`-ендпоінта: редагуванням `BookSeeder`.
> `curl` на цьому занятті лише **читає** дані (`GET`), а не створює їх.

---

## Крок 7. Створити маппер і сервіс

**Маппер** (перетворення `Book` → DTO):
```java
// service/mapper/BookMapper.java
package com.ua.bookstore.service.mapper;

import com.ua.bookstore.domain.Book;
import com.ua.bookstore.web.dto.BookCardDto;
import com.ua.bookstore.web.dto.BookDetailDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookCardDto toCard(Book b) {
        return new BookCardDto(b.getId(), b.getTitle(), b.getAuthor(),
                b.getPrice(), b.getCoverUrl());
    }

    public BookDetailDto toDetail(Book b) {
        return new BookDetailDto(b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(),
                b.getYear(), b.getCoverUrl(), b.getDescription(), b.getGenre());
    }
}
```

**Сервіс** (інтерфейс + реалізація):
```java
// service/BookService.java
package com.ua.bookstore.service;

import com.ua.bookstore.web.dto.BookCardDto;
import com.ua.bookstore.web.dto.BookDetailDto;
import java.util.List;

public interface BookService {
    List<BookCardDto> findAll();
    BookDetailDto findById(Long id);
}
```

```java
// service/BookServiceImpl.java
package com.ua.bookstore.service;

import com.ua.bookstore.domain.Book;
import com.ua.bookstore.repository.BookRepository;
import com.ua.bookstore.service.mapper.BookMapper;
import com.ua.bookstore.web.dto.BookCardDto;
import com.ua.bookstore.web.dto.BookDetailDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    public BookServiceImpl(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<BookCardDto> findAll() {
        return repository.findAll().stream().map(mapper::toCard).toList();
    }

    @Override
    public BookDetailDto findById(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return mapper.toDetail(book);
    }
}
```

**Виняток** для 404:
```java
// exception/BookNotFoundException.java
package com.ua.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Книгу з id=" + id + " не знайдено");
    }
}
```
(імпортуйте `BookNotFoundException` у `BookServiceImpl`).

---

## Крок 8. Створити контролер з двома ендпоінтами

```java
// web/BookController.java
package com.ua.bookstore.web;

import com.ua.bookstore.service.BookService;
import com.ua.bookstore.web.dto.BookCardDto;
import com.ua.bookstore.web.dto.BookDetailDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookCardDto> list() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookDetailDto getOne(@PathVariable Long id) {
        return bookService.findById(id);
    }
}
```

---

## Крок 9. Запустити й перевірити через curl

Запустіть застосунок. Перевірте обидва ендпоінти:

```bash
# перелік книг
curl http://localhost:8080/api/books

# одна книга з деталями
curl http://localhost:8080/api/books/1

# неіснуюча книга → 404
curl -i http://localhost:8080/api/books/999
```

Очікувано: перший запит повертає масив карток, другий — одну книгу з деталями,
третій — статус `404`.

---

## Що здати

1. Посилання на **ваш** git-репозиторій (окремий від репозиторію викладача).
2. Працюючі два ендпоінти (`GET /api/<ваша-сутність>` та `.../{id}`).
3. Файл `requests.md` у вашому проєкті з прикладами `curl` до ваших ендпоінтів.
4. Початкове наповнення — 4–5 власних записів із заповненими полями для картки та
   сторінки.

## Критерії

- Дотримано багатошарову структуру (domain / repository / service / web) та інтерфейси
  на межах.
- Назовні повертаються DTO, а не доменні об'єкти.
- Обидва ендпоінти працюють; неіснуючий id дає 404.
- Предметна область **власна** (поля відрізняються від еталона).

> UI (React) до цього проєкту ви додасте окремою самостійною роботою після наступного
> заняття. Поки що — лише backend.
