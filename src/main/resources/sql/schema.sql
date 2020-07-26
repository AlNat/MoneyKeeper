-----------------------------------
-- Схема данных
-- Индексы и триггерные функции расположены в отдельных файлах
-----------------------------------

CREATE DATABASE moneykeeper;


-- Создание пользователя
CREATE USER moneykeeper WITH PASSWORD 'moneykeeper';
GRANT ALL PRIVILEGES ON DATABASE "moneykeeper" to moneykeeper;

-- Таблица счет
create table account
(
    accountid   serial primary key,
    created     timestamp,
    updated     timestamp,
    description varchar(255),
    name        varchar(255) not null unique,
    type        integer      not null
);
alter table account owner to moneykeeper;

-- Таблица категории покупки
create table category
(
    categoryid        serial primary key,
    created           timestamp,
    updated           timestamp,
    description       varchar(255),
    name              varchar(255) not null unique,
    parentcategory_id integer
);
alter table category owner to moneykeeper;


-- Таблица транзакции
create table transaction
(
    transactionid serial         not null primary key,
    created       timestamp,
    updated       timestamp,
    amount        numeric(19, 2) not null,
    comment       varchar(255),
    processdate   timestamp      not null,
    status        integer        not null,
    type          integer        not null,
    accountid     integer        not null references account(accountid),
    categoryid    integer        references category(categoryid)
);
alter table transaction owner to moneykeeper;