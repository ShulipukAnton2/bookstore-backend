# Приклади запитів — повний CRUD книжок

Запустіть backend на Java 21: `./mvnw spring-boot:run` (macOS/Linux) або `.\mvnw.cmd spring-boot:run` (Windows PowerShell). Сервер використовує порт 8080.

## Перелік книжок

```bash
curl http://localhost:8080/api/books
```

## Фільтр за жанром

```bash
curl "http://localhost:8080/api/books?genre=Поезія"
```

## Одна книжка

```bash
curl http://localhost:8080/api/books/1
```

## Створення (201 Created)

```bash
curl -i -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Нова книга","author":"Автор","price":199.00,"year":2024,"genre":"Проза","coverUrl":"/covers/new-book.jpg","description":"Опис книги."}'
```

## Оновлення (200 OK; для відсутнього id — 404)

```bash
curl -i -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Оновлена книга","author":"Автор","price":250.00,"year":2024,"genre":"Проза","coverUrl":"/covers/new-book.jpg","description":"Оновлений опис."}'

curl -i -X PUT http://localhost:8080/api/books/999 \
  -H "Content-Type: application/json" \
  -d '{"title":"X","author":"Y","price":1,"year":2024,"genre":"Проза"}'
```

## Видалення (204 No Content; для відсутнього id — 404)

```bash
curl -i -X DELETE http://localhost:8080/api/books/2
curl -i -X DELETE http://localhost:8080/api/books/999
```
