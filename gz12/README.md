# Backend книгарні — заняття 1.2

Навчальний Spring Boot backend для роботи з книжками без бази даних. Дані зберігаються в пам'яті; після перезапуску початковий набір відновлюється.

## Запуск

Потрібна Java 21. Maven Wrapper завантажить Maven під час першого запуску:

```bash
./mvnw spring-boot:run      # macOS/Linux
```

У Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Сервер слухає порт `8080`. Перелік прикладів запитів є у [requests.md](requests.md).

## API

- `GET /api/books` — список; необов'язковий фільтр `?genre=Поезія`.
- `GET /api/books/{id}` — деталі книжки.
- `POST /api/books` — створення, `201 Created`.
- `PUT /api/books/{id}` — повне оновлення, `200 OK`.
- `DELETE /api/books/{id}` — видалення, `204 No Content`.
- Для відсутньої книжки `GET`, `PUT` і `DELETE` повертають `404 Not Found`.

Тіло `POST` і `PUT` відповідає `BookRequest` та не містить `id`; сервер призначає його під час створення.
