# Самостійна робота до заняття 1.2 — повний CRUD (лише backend)

## Мета

Доповнити **власний проєкт** із заняття 1.1 до **повного CRUD**: додати створення,
оновлення та видалення, а також фільтр за категорією. Лише backend, у **власному
репозиторії**, на **власній предметній області**.

Наскрізний приклад — та сама книгарня (сутність `Book`), що й у попередній самостійній.

## Передумови

У вас уже є проєкт із заняття 1.1: сутність, два `GET`-ендпоінти, шари
domain / repository / service / web, сидер. Тепер нарощуємо його.

---

## Що має вийти

Повний набір ендпоінтів:

![CRUD для книгарні](./media-student/book-crud-endpoints.svg)

Окремий вхідний DTO для створення/оновлення:

![Вхідний DTO vs вихідні DTO](./media-student/request-dto.svg)

---

## Крок 1. Додати поле категорії в модель

У клас `Book` додайте поле для фільтрації (напр. `genre` — жанр). Згенеруйте гетер/сетер
і додайте його в конструктор. Оновіть сидер — заповніть `genre` у стартових книгах.

## Крок 2. Розширити репозиторій

У інтерфейс `BookRepository` і його in-memory реалізацію додайте:

```java
List<Book> findByGenre(String genre);
boolean deleteById(Long id);
```

Реалізація (in-memory):
```java
@Override
public List<Book> findByGenre(String genre) {
    return storage.values().stream()
            .filter(b -> genre.equalsIgnoreCase(b.getGenre()))
            .toList();
}

@Override
public boolean deleteById(Long id) {
    return storage.remove(id) != null;
}
```

## Крок 3. Створити вхідний DTO

Окремий `record` для тіла запиту — **без `id`** (його призначає сервер):

```java
// web/dto/BookRequest.java
public record BookRequest(
        String title, String author, java.math.BigDecimal price,
        Integer year, String genre, String coverUrl, String description
) {}
```

## Крок 4. Додати мапінг вхідного DTO в домен

У `BookMapper` додайте метод `toDomain`:

```java
public Book toDomain(BookRequest r) {
    Book b = new Book();
    b.setTitle(r.title());
    b.setAuthor(r.author());
    b.setPrice(r.price());
    b.setYear(r.year());
    b.setGenre(r.genre());
    b.setCoverUrl(r.coverUrl());
    b.setDescription(r.description());
    return b;   // id не встановлюємо тут
}
```

## Крок 5. Розширити сервіс

Інтерфейс `BookService` — додайте методи:
```java
List<BookCardDto> findAll(String genre);   // фільтр за жанром (null = усі)
BookDetailDto create(BookRequest request);
BookDetailDto update(Long id, BookRequest request);
void delete(Long id);
```

Реалізація (`BookServiceImpl`):
```java
@Override
public List<BookCardDto> findAll(String genre) {
    List<Book> books = (genre == null || genre.isBlank())
            ? repository.findAll()
            : repository.findByGenre(genre);
    return books.stream().map(mapper::toCard).toList();
}

@Override
public BookDetailDto create(BookRequest request) {
    Book book = mapper.toDomain(request);
    book.setId(null);
    return mapper.toDetail(repository.save(book));
}

@Override
public BookDetailDto update(Long id, BookRequest request) {
    repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    Book book = mapper.toDomain(request);
    book.setId(id);
    return mapper.toDetail(repository.save(book));
}

@Override
public void delete(Long id) {
    if (!repository.deleteById(id)) {
        throw new BookNotFoundException(id);
    }
}
```

## Крок 6. Додати ендпоінти в контролер

```java
@GetMapping
public List<BookCardDto> list(@RequestParam(required = false) String genre) {
    return bookService.findAll(genre);
}

@PostMapping
@ResponseStatus(HttpStatus.CREATED)                 // 201
public BookDetailDto create(@RequestBody BookRequest request) {
    return bookService.create(request);
}

@PutMapping("/{id}")
public BookDetailDto update(@PathVariable Long id, @RequestBody BookRequest request) {
    return bookService.update(id, request);          // 200, або 404 якщо немає
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
    bookService.delete(id);
    return ResponseEntity.noContent().build();       // 204
}
```

## Крок 7. Перевірити через curl

```bash
# фільтр за жанром
curl "http://localhost:8080/api/books?genre=Поезія"

# створити (201)
curl -i -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Нова книга","author":"Автор","price":199.00,"year":2024,"genre":"Проза","coverUrl":"/covers/x.jpg","description":"Опис"}'

# оновити (200 / 404)
curl -i -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Оновлена","author":"Автор","price":250.00,"year":2024,"genre":"Проза","coverUrl":"/covers/x.jpg","description":"Опис"}'

# видалити (204 / 404)
curl -i -X DELETE http://localhost:8080/api/books/2
```

---

## Що здати

1. У **вашому** репозиторії — повний CRUD вашої сутності (5 ендпоінтів) + фільтр за полем.
2. Окремий вхідний DTO (`...Request`) без `id`.
3. Коректні коди відповіді: `201` на створення, `204` на видалення, `404` на неіснуючий id.
4. Оновлений `requests.md` із прикладами на всі шість операцій.

## Критерії

- Дотримано шари й інтерфейси (як на 1.1).
- Вхідні дані читаються через `@RequestBody`, фільтр — через `@RequestParam`.
- `update` і `delete` для неіснуючого id повертають `404`.
- Предметна область власна (поля відрізняються від еталона).

> UI для CRUD (форма створення, кнопка видалення) — за бажанням; основне завдання — backend.
