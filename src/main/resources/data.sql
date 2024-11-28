INSERT INTO singleton_value (id, car_count)
SELECT 1, 0
FROM DUAL
WHERE (SELECT COUNT(*) FROM singleton_value) = 0;
