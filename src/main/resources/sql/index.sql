-- Индексы на аккаунт
create unique index account_name_uidx on account (name);
create index account_type_idx on account (type);
create index account_created_idx on account (created DESC NULLS LAST);


-- Индексы на категорию
create unique index category_name_uidx on category (name);
create index category_created_idx on category (created DESC NULLS LAST);


-- Индексы на транзакции
create index transaction_processdate_idx on transaction (created DESC NULLS LAST);
create index transaction_type_idx on transaction (type);
-- и на их FK - postgres по дефолту такое не делает
create index transaction_accountid_idx on transaction (accountid);
create index transaction_categoryid_idx on transaction (categoryid);
