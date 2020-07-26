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
    INSERT INTO activity_dates(day)
    SELECT t.day::date
    FROM generate_series
             ((SELECT a.created FROM account a WHERE a.accountid = account)::timestamp,
              (SELECT max(t.processdate) FROM transaction t WHERE t.accountid = account),
              '1 day'::interval) AS t(day);

    RETURN QUERY
        SELECT
            ad.day,
            -- Находим нарастающим итогом сумма баланса по счету
            -- через оконные функции
            coalesce(SUM(balance) OVER w, 0) AS balance
        FROM activity_dates ad
                 LEFT JOIN
             (
                 SELECT t.processdate::date as d,
                        coalesce(sum(t.amount), 0) AS balance
                 FROM transaction t
                 WHERE t.accountid = account
                 GROUP BY t.processdate
             ) as b ON ad.day = b.d
            WINDOW w AS (ORDER BY ad.day ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW);
END
$BODY$
    LANGUAGE plpgsql;
ALTER FUNCTION public.account_balance() OWNER TO moneykeeper;



