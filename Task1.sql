-- This query computes the number of transactions the user had within the previous seven days, for every user transaction
-- Both the dates in 7 day period are inclusive
-- If there are multiple transactions on same day, the number of transactions does not increase when we see the transaction for that day but for other days they are summed up.


SELECT
    transaction_id,
    user_id,
    date,
    (
        SELECT
            COUNT(*)
        FROM
            transactions t2
        WHERE
            t2.user_id = t1.user_id --  AND t2.user_id = 'c682ef45-3a7e-4230-a6c7-25ead19d58d6'
            AND t2.date >= DATE_SUB(t1.date, INTERVAL 7 DAY)
            AND t2.date <= t1.date
    ) AS no_txn_last_7days
FROM
    transactions t1 -- WHERE t1.user_id = 'c682ef45-3a7e-4230-a6c7-25ead19d58d6'
order by
    t1.user_id,
    date;

