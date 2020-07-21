
INSERT INTO account(description, name)
VALUES ('Наличная валюта в кошельке', 'Кошелек'),
       ('Банковская карта ООО Банк', 'Карта');


INSERT INTO category(description, name)
VALUES ('Тестовая категория', 'Категория');

INSERT INTO transaction(amount, comment, processdate, status, type, accountid, categoryid)
VALUES (100.00, 'Пополнение счета', current_timestamp, 0, 0, 1, 1),
       (10.00,  'Тестовая покупка', current_timestamp, 0, 1, 1, 1);