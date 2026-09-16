# Дипломный проект: автоматизация тестирования комплексного сервиса «Путешествие дня»

## Описание проекта

Проект представляет собой автоматизацию тестирования веб-сервиса **«Путешествие дня»**, который предлагает купить тур двумя способами:

- **Обычная оплата** по дебетовой карте (Payment Gate);
- **Выдача кредита** по данным банковской карты (Credit Gate).

Приложение не обрабатывает данные карт самостоятельно, а пересылает их банковским сервисам-эмуляторам. В собственной СУБД приложение сохраняет информацию о том, успешно ли был совершён платёж и каким способом.

### 📚 Проектная документация

- [План автоматизации](docs/Plan.md)
- [Отчёт о тестировании](docs/Report.md)
- [Отчёт об автоматизации](docs/Summary.md)

---

## Начало работы

Инструкция, как получить копию проекта для запуска на локальном ПК.

### Prerequisites

Что нужно установить на ПК:

- **Git** — для клонирования репозитория;
- **Docker** и **Docker Compose** — для запуска СУБД и эмулятора банка;
- **Java 11+** (JDK) — для запуска SUT и автотестов;
- **Node.js 18+** и **npm** — для запуска эмулятора банковских сервисов;
- **Браузер** (Chrome или Firefox) — для UI-тестов.

---

## Установка и запуск

### 1. Клонировать репозиторий

git clone https://github.com/alinasadness-cpu/diploma.git
cd diploma

### 2. Запустить контейнеры (MySQL, PostgreSQL, gate-simulator)
docker-compose up -d

Проверить, что контейнеры запущены:
docker ps

Ожидаемый результат:

CONTAINER ID   IMAGE              PORTS                    NAMES
xxxxxxxxxxxx   mysql:8.0          0.0.0.0:3306->3306/tcp   mysql-diploma
xxxxxxxxxxxx   postgres:15        0.0.0.0:5432->5432/tcp   postgres-diploma
xxxxxxxxxxxx   node:18-alpine     0.0.0.0:9999->9999/tcp   gate-simulator

### 3. Запустить тестируемый сервис (SUT)

java -jar aqa-shop.jar

SUT запустится на порту **8080**. Проверить:

curl http://localhost:8080

### 4. Запустить автотесты

./gradlew test

### 5. Посмотреть отчёт Allure

./gradlew allureServe

## Примеры

### Запуск одного теста

./gradlew test --tests "ru.netology.test.PaymentTest"

### Запуск тестов в headless-режиме

./gradlew test -Dselenide.headless=true

### Запуск SUT с PostgreSQL

По умолчанию SUT подключается к MySQL. Для использования PostgreSQL отредактируйте `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/app
spring.datasource.username=app
spring.datasource.password=pass
```

### Запуск эмулятора банка вручную (без Docker Compose)

cd gate-simulator
npm install
npm start

Эмулятор запустится на порту **9999**.

---

## Состав проекта

| Компонент | Описание |
|-----------|----------|
| `aqa-shop.jar` | Тестируемый сервис |
| `gate-simulator/` | Эмулятор банковских сервисов (Node.js) |
| `docker-compose.yml` | Конфигурация MySQL, PostgreSQL, gate-simulator |
| `src/test/java/` | Автотесты |
| `docs/Plan.md` | План автоматизации |
| `docs/Report.md` | Отчёт о тестировании |
| `docs/Summary.md` | Отчёт об автоматизации |

---

## Лицензия

Проект создан в учебных целях в рамках дипломной работы по профессии «Тестировщик» (Нетология). Коммерческое использование не предполагается.
