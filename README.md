# Campaign Dashboard

Aplikacja webowa do zarządzania produktami i kampaniami reklamowymi z budżetem opartym na saldzie konta demo.

## Dokumentacja

- [Kontrakt API (OpenAPI)](docs/contract.yaml)
- [Model bazy danych](docs/database.md)

## Wymagania

- Docker oraz Docker Compose (v2).

## Uruchomienie

```bash
docker compose up --build
```

Po uruchomieniu:

| Usługa | URL |
|---|---|
| Frontend | http://localhost |
| Backend API | http://localhost/api |


## Zmienne środowiskowe (opcjonalne)

Backend czyta plik `.env` z katalogu głównego. Dostępne zmienne:

| Zmienna | Domyślna wartość | Opis |
|---|---|---|
| `APP_EMERALD_SEED_BALANCE` | `10000.00` | Saldo startowe konta demo |
| `APP_EMERALD_CURRENCY` | `USD` | Waluta konta demo |

Przykładowy `.env`:

```env
APP_EMERALD_SEED_BALANCE=5000.00
APP_EMERALD_CURRENCY=EUR
```
