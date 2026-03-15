# Model bazy danych (H2 in-memory)

Ten dokument opisuje docelowy model danych dla aplikacji Campaign Dashboard zgodny z ustaleniami:

1. Jedno globalne konto demo (bez logowania),
2. CRUD produktow oraz kampanii od nich nalezacych.

## Założenia

- Silnik: H2 uruchamiany in memory.
- Relacja produktu do kampanii: **1:N** (jeden produkt może mieć wiele kampanii).
- Kampania ma słownikowe miasto i listę słów kluczowych.
- `campaign_fund` jest rezerwowany z konta Emerald przy tworzeniu/edycji kampanii.
- Przy usunięciu kampanii fund wraca na konto demo.

## Encje i tabele

## 1) emerald_account
Globalne konto demo (jeden rekord).

| Kolumna | Typ | Ograniczenia | Opis |
|---|---|---|---|
| id | UUID | PK | UUID konta |
| balance | DECIMAL(19,2) | NOT NULL, CHECK (balance >= 0) | Aktualne saldo |
| currency | VARCHAR(20) | NOT NULL | Np. `USD` |

## 2) product
Produkty, dla których można tworzyć kampanie.

| Kolumna | Typ | Ograniczenia | Opis |
|---|---|---|---|
| id | UUID | PK | UUID produktu |
| name | VARCHAR(100) | NOT NULL | Nazwa produktu |
| created_at | TIMESTAMP | NOT NULL | Data utworzenia |
| updated_at | TIMESTAMP | NOT NULL | Data modyfikacji |
| emerald_account_id | UUID | NOT NULL, FK -> emerald_account(id) | UUID wlasciciela produktu |



## 3) town
Słownik miast do dropdown.

| Kolumna | Typ | Ograniczenia | Opis |
|---|---|---|---|
| id | UUID | PK | UUID miasta |
| name | VARCHAR(120) | NOT NULL, UNIQUE | Nazwa miasta |

## 4) keyword
Słownik słów kluczowych do typeahead.

| Kolumna | Typ | Ograniczenia | Opis |
|---|---|---|---|
| id | UUID | PK | UUID słowa kluczowego |
| keyword_value | VARCHAR(120) | NOT NULL, UNIQUE | Treść słowa kluczowego |

## 5) campaign
Kampania reklamowa przypisana do produktu.

| Kolumna | Typ | Ograniczenia | Opis |
|---|---|---|---|
| id | UUID | PK | UUID kampanii |
| product_id | UUID | NOT NULL, FK -> product(id) | Produkt kampanii |
| name | VARCHAR(120) | NOT NULL | Nazwa kampanii |
| bid_amount | DECIMAL(19,2) | NOT NULL, CHECK (bid_amount >= 0.01) | Stawka bid |
| campaign_fund | DECIMAL(19,2) | NOT NULL, CHECK (campaign_fund > 0) | Budżet kampanii |
| status | VARCHAR(10) | NOT NULL, CHECK (status IN ('ON','OFF')) | Status kampanii |
| town_id | UUID | NOT NULL, FK -> town(id) | Miasto |
| radius_km | INT | NOT NULL, CHECK (radius_km > 0) | Promień w km |
| created_at | TIMESTAMP | NOT NULL | Data utworzenia |
| updated_at | TIMESTAMP | NOT NULL | Data modyfikacji |

## 6) campaign_keyword
Tabela łącząca kampanie i słowa kluczowe (M:N).

| Kolumna | Typ | Ograniczenia | Opis |
|---|---|---|---|
| campaign_id | UUID | PK, FK -> campaign(id) | Kampania |
| keyword_id | UUID | PK, FK -> keyword(id) | Słowo kluczowe |

## Relacje

- `emerald_account (1) -> (N) product`
- `product (1) -> (N) campaign`
- `town (1) -> (N) campaign`
- `campaign (N) -> (N) keyword` 

## Inne reguly biznesowe

1. Kampania musi mieć co najmniej 1 keyword.
2. Przy `POST /campaigns`:
   - jeśli `campaign_fund` > saldo konta, zwracamy błąd (HTTP 409).
   - w przeciwnym razie odejmujemy fund od konta.
3. Przy `PUT /campaigns/{id}`:
   - liczymy różnicę fund (`newFund - oldFund`) i aktualizujemy saldo.
4. Przy `DELETE /campaigns/{id}`:
   - fund usuwanej kampanii wraca na konto.

## Dane startowe

- `emerald_account`: 1 rekord (np. balance = 10000.00, currency = 'USD')
- `town`: Warsaw, Krakow, Gdansk, Wroclaw, Poznan
- `keyword`: socialmedia, drink, food, business, saas
