-----------------------------------
-- Схема данных
-- Индексы и триггерные функции расположены в отдельных файлах
-----------------------------------

CREATE DATABASE moneykeeper;

-- TODO Переключиться

-- Создание пользователя
CREATE USER moneykeeper WITH PASSWORD 'moneykeeper';
GRANT ALL PRIVILEGES ON DATABASE "moneykeeper" to moneykeeper;