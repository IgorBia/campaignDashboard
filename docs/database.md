# Database model (H2 in-memory)

This document describes the target data model for the Campaign Dashboard application based on the following assumptions:

1. One global demo account (no login),
2. CRUD of products and their campaigns.

## Assumptions

- Engine: H2 running in memory.
- Product-to-campaign relationship: **1:N** (one product can have many campaigns).
- A campaign has a dictionary town and a list of keywords.
- `campaign_fund` is reserved from the Emerald account when creating/updating a campaign.
- When deleting a campaign, the fund returns to the demo account.

## Entities and tables

## 1) emerald_account
Global demo account (single record).

| Column | Type | Constraints | Description |
|---|---|---|---|
| id | UUID | PK | Account UUID |
| balance | DECIMAL(19,2) | NOT NULL, CHECK (balance >= 0) | Current balance |
| currency | VARCHAR(20) | NOT NULL | e.g. `USD` |
| created_at | TIMESTAMP | NOT NULL | Creation date |
| updated_at | TIMESTAMP | NOT NULL | Update date |

## 2) product
Products for which campaigns can be created.

| Column | Type | Constraints | Description |
|---|---|---|---|
| id | UUID | PK | Product UUID |
| name | VARCHAR(100) | NOT NULL | Product name |
| created_at | TIMESTAMP | NOT NULL | Creation date |
| updated_at | TIMESTAMP | NOT NULL | Update date |
| emerald_account_id | UUID | NOT NULL, FK -> emerald_account(id) | Product owner UUID |

## 3) town
City dictionary for dropdown.

| Column | Type | Constraints | Description |
|---|---|---|---|
| id | UUID | PK | Town UUID |
| name | VARCHAR(120) | NOT NULL, UNIQUE | Town name |

## 4) keyword
Keyword dictionary for typeahead.

| Column | Type | Constraints | Description |
|---|---|---|---|
| id | UUID | PK | Keyword UUID |
| keyword_value | VARCHAR(120) | NOT NULL, UNIQUE | Keyword value |

## 5) campaign
Advertising campaign assigned to a product.

| Column | Type | Constraints | Description |
|---|---|---|---|
| id | UUID | PK | Campaign UUID |
| product_id | UUID | NOT NULL, FK -> product(id) | Campaign product |
| name | VARCHAR(120) | NOT NULL | Campaign name |
| bid_amount | DECIMAL(19,2) | NOT NULL, CHECK (bid_amount >= 0.01) | Bid amount |
| campaign_fund | DECIMAL(19,2) | NOT NULL, CHECK (campaign_fund > 0) | Campaign budget |
| status | VARCHAR(10) | NOT NULL, CHECK (status IN ('ON','OFF')) | Campaign status |
| town_id | UUID | NOT NULL, FK -> town(id) | Town |
| radius_km | INT | NOT NULL, CHECK (radius_km > 0) | Radius in km |
| created_at | TIMESTAMP | NOT NULL | Creation date |
| updated_at | TIMESTAMP | NOT NULL | Update date |

## 6) campaign_keyword
Join table for campaigns and keywords (M:N).

| Column | Type | Constraints | Description |
|---|---|---|---|
| campaign_id | UUID | PK, FK -> campaign(id) | Campaign |
| keyword_id | UUID | PK, FK -> keyword(id) | Keyword |

## Relationships

- `emerald_account (1) -> (N) product`
- `product (1) -> (N) campaign`
- `town (1) -> (N) campaign`
- `campaign (N) -> (N) keyword` 

## Other business rules

1. A campaign must have at least 1 keyword.
2. On `POST /campaigns`:
   - if `campaign_fund` > account balance, return an error (HTTP 409).
   - otherwise, subtract the fund from the account.
3. On `PUT /campaigns/{id}`:
   - compute the fund difference (`newFund - oldFund`) and update the balance.
4. On `DELETE /campaigns/{id}`:
   - the fund of the deleted campaign returns to the account.

## Seed data

- `emerald_account`: 1 record (balance = 10000.00, currency = 'USD') — inserted by `EmeraldAccountInitializer` at startup
- `town` (10 records): Warsaw, Krakow, Gdansk, Wroclaw, Poznan, Lodz, Szczecin, Bydgoszcz, Lublin, Katowice
- `keyword` (25 records): food, clothing, electronics, furniture, toys, books, sports, beauty, automotive, health, garden, pet supplies, office supplies, baby products, jewelry, music, movies, video games, home decor, tools, outdoors, travel, services, survival, other
