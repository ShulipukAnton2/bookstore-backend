# Приклади запитів — книгарня

Запустіть бекенд із кореня проєкту командою `mvn spring-boot:run`. За замовчуванням API доступне на `http://localhost:8080`.

## 1. Перелік книг (короткі картки)

```bash
curl http://localhost:8080/api/books
```

Повертає масив із п'яти карток: `id`, `title`, `author`, `price`, `coverUrl`.

## 2. Одна книга з повною інформацією

```bash
curl http://localhost:8080/api/books/1
```

Відповідь містить також `year`, `description` і `genre`.

## 3. Книги з таким ID немає

```bash
curl -i http://localhost:8080/api/books/999
```

Очікуваний статус: `404 Not Found`.
