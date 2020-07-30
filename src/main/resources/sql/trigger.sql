-----------------------------------
-- Триггерные функции
-----------------------------------

CREATE OR REPLACE FUNCTION public.create_trigger()
    RETURNS trigger AS
$BODY$
BEGIN
    NEW.created = now();
    NEW.updated = NEW.created;
    RETURN NEW;
END
$BODY$
    LANGUAGE plpgsql;
ALTER FUNCTION public.create_trigger() OWNER TO moneykeeper;

CREATE OR REPLACE FUNCTION public.update_trigger()
    RETURNS trigger AS
$BODY$
BEGIN
    NEW.created = OLD.created;
    NEW.updated = now();
    RETURN NEW;
END
$BODY$
    LANGUAGE plpgsql;
ALTER FUNCTION public.update_trigger() OWNER TO moneykeeper;


-----------------------------------
-- Сами триггеры на все таблицы
-- Сокращение - name_type_trg
-- где type - или created или updated
-- а name - имя таблицы
-----------------------------------


-- Триггеры для transaction

DROP TRIGGER IF EXISTS transaction_create_trg on transaction;
CREATE TRIGGER transaction_create_trg
    BEFORE INSERT
    ON transaction
    FOR EACH ROW
EXECUTE PROCEDURE create_trigger();

DROP TRIGGER IF EXISTS transaction_update_trg on transaction;
CREATE TRIGGER transaction_update_trg
    BEFORE UPDATE
    ON transaction
    FOR EACH ROW
EXECUTE PROCEDURE update_trigger();


-- Триггеры для account

DROP TRIGGER IF EXISTS account_create_trigger on account;
CREATE TRIGGER account_create_trigger
    BEFORE INSERT
    ON account
    FOR EACH ROW
EXECUTE PROCEDURE create_trigger();

DROP TRIGGER IF EXISTS account_update_trigger on account;
CREATE TRIGGER account_update_trigger
    BEFORE UPDATE
    ON account
    FOR EACH ROW
EXECUTE PROCEDURE update_trigger();



-- Триггеры для category

DROP TRIGGER IF EXISTS category_create_trigger on category;
CREATE TRIGGER category_create_trigger
    BEFORE INSERT
    ON category
    FOR EACH ROW
EXECUTE PROCEDURE create_trigger();

DROP TRIGGER IF EXISTS category_update_trigger on category;
CREATE TRIGGER category_update_trigger
    BEFORE UPDATE
    ON category
    FOR EACH ROW
EXECUTE PROCEDURE update_trigger();
