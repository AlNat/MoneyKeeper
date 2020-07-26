INSERT INTO account(description, name, type)
VALUES ('Наличная валюта в кошельке', 'Кошелек', 0),
       ('Банковская карта ООО Банк', 'Карта', 1);

INSERT INTO category(description, name)
VALUES ('Тестовая категория', 'Категория');

INSERT INTO transaction(amount, comment, processdate, status, type, accountid, categoryid)
VALUES (100.00, 'Пополнение счета', current_timestamp, 0, 0, 1, 1),
       (-10.00,  'Тестовая покупка', current_timestamp + INTERVAL '1 day', 0, 1, 1, 1),
       (-10.00,  'Тестовая покупка', current_timestamp + INTERVAL '3 day', 0, 1, 1, 1),
       (-10.00,  'Тестовая покупка', current_timestamp + INTERVAL '5 day', 0, 1, 1, 1);
