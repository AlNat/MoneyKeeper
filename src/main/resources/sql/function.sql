-----------------------------------------------------------------
-- Функция расчета баланса по счету на каждый день его активности
-----------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.account_balance(account integer)
RETURNS TABLE(day date, balance numeric(19, 2)) AS
    $BODY$
    BEGIN
        -- Создаем список активных дней у счета
        CREATE TEMPORARY TABLE activity_dates (day date) ON COMMIT DROP;

        -- Для счета находим список его активных дней
        RETURN QUERY
            SELECT
                ad.day as date,
                -- Находим нарастающим итогом сумма баланса по счету
                -- через оконные функции
                coalesce(SUM(dayBalance) OVER w, 0) AS balanace
            FROM activity_dates ad
            LEFT JOIN (
                SELECT t.processdate::date as day,
                       coalesce(sum(t.amount), 0) AS dayBalance
                FROM transaction t
                WHERE t.accountid = account
                GROUP BY t.processdate
            ) as b ON ad.day = b.day
            WINDOW w AS (ORDER BY ad.day ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
            ORDER BY ad.day;

        INSERT INTO activity_dates(day)
        SELECT t.day::date
        FROM generate_series
                 ((SELECT a.created FROM account a WHERE a.accountid = account)::timestamp,
                  (SELECT max(t.processdate) FROM transaction t WHERE t.accountid = account),
                  '1 day'::interval) AS t(day);
    END
    $BODY$
LANGUAGE plpgsql;
ALTER FUNCTION public.account_balance(int) OWNER TO moneykeeper;


-----------------------------------------------------------------
-- Функция расчета баланса по счету на каждый день из выборки
-----------------------------------------------------------------

CREATE OR REPLACE FUNCTION public.account_balance(account integer, fromDate date, toDate date)
    RETURNS TABLE(day date, balance numeric(19, 2)) AS
$BODY$
BEGIN
    -- Создаем список активных дней у счета
    CREATE TEMPORARY TABLE activity_dates (day date) ON COMMIT DROP;

    -- Для счета находим список его активных дней
    INSERT INTO activity_dates(day)
    SELECT t.day::date
    FROM generate_series
             (fromDate::timestamp,
              toDate::timestamp,
              '1 day'::interval) AS t(day);

    RETURN QUERY
        SELECT
            ad.day as date,
            -- Находим нарастающим итогом сумма баланса по счету
            -- через оконные функции
            coalesce(SUM(dayBalance) OVER w, 0) AS balance
        FROM activity_dates ad
        LEFT JOIN (
            SELECT t.processdate::date as day,
                   coalesce(sum(t.amount), 0) AS dayBalance
            FROM transaction t
            WHERE t.accountid = account
            GROUP BY t.processdate
        ) as b ON ad.day = b.day
        WINDOW w AS (ORDER BY ad.day ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
        ORDER BY ad.day;
END
$BODY$
    LANGUAGE plpgsql;
ALTER FUNCTION public.account_balance(int, date, date) OWNER TO moneykeeper;
