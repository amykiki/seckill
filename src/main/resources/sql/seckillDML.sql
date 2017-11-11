SELECT * FROM seckill;

UPDATE seckill
SET
  number = 5
#   start_time = '2017-11-12 00:00:00',
#   end_time   = '2017-11-12 02:59:59'
WHERE seckill_id = 1000;

UPDATE seckill
SET
  start_time = '2017-11-13 15:00:00',
  end_time   = '2017-11-13 20:59:59'
WHERE seckill_id = 1001;

SELECT * FROM success_killed;

DELETE FROM success_killed WHERE seckill_id = 1000 AND user_phone = 18930161864;