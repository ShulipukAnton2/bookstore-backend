# Bookstore REST API

Навчальний backend інтернет-книгарні на Spring Boot 3.4.13 та Java 21. Дані зберігаються в пам'яті й наповнюються п'ятьма книгами під час запуску.

## Запуск

Потрібні JDK 21, Maven 3.6.3 або новіший і Visual Studio Code. У VS Code встановіть рекомендовані розширення Java Extension Pack та Spring Boot Extension Pack, потім відкрийте **цю теку** (`pom.xml`) як проєкт.

Запуск із меню **Run and Debug**: виберіть **Запустити Bookstore API** і натисніть зелену кнопку. Альтернативно, у вбудованому терміналі VS Code виконайте:

```bash
mvn spring-boot:run
```

## API

- `GET /api/books` — картки всіх книг.
- `GET /api/books/{id}` — повні дані однієї книги; невідомий ID повертає `404`.

Приклади `curl` наведені у [requests.md](./requests.md).

Якщо VS Code повідомляє, що не знайдено Java або Maven, встановіть JDK 21 і Maven, перезапустіть VS Code та відкрийте теку знову. Рекомендовані розширення вказані у `.vscode/extensions.json`.

## Структура

`domain` містить модель книги; `repository` — контракт і сховище в пам'яті; `service` — бізнес-логіку та маппер; `web` — контролер і DTO; `config` — початкове наповнення; `exception` — обробку 404.
