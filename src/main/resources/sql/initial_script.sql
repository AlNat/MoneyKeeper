INSERT INTO account(key, description, name, type)
VALUES ('test', 'Наличная валюта в кошельке', 'Кошелек', 0),
       ('card', 'Банковская карта ООО Банк',  'Карта',   1);

INSERT INTO category(key, description, name)
VALUES ('test', 'Тестовая категория', 'Категория'),
       ('test_2', 'Тестовая категория 2', 'Категория 2');

INSERT INTO transaction(amount, comment, processdate, status, type, accountid, categoryid)
VALUES (100.00, 'Пополнение счета', current_timestamp, 0, 0, 1, NULL),
       (100.00, 'Пополнение счета', current_timestamp, 0, 0, 2, NULL),
       (-10.00,  'Тестовая покупка', current_timestamp + INTERVAL '1 day', 0, 1, 1, 1),
       (-10.00,  'Тестовая покупка', current_timestamp + INTERVAL '3 day', 0, 1, 1, 1),
       (1000.00, 'Пополнение счета', current_timestamp + INTERVAL '3 day', 0, 0, 2, NULL),
       (-10.00,  'Тестовая покупка', current_timestamp + INTERVAL '5 day', 0, 1, 1, 2);
