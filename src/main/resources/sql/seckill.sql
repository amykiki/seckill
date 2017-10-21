use seckill;
# 创建秒杀库存表 默认MySQL有多种存储引擎，但支持事务的只有InnoDb
CREATE TABLE seckill (
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`seckill_id`),
  KEY idx_start_time (`start_time`),
  KEY ide_end_time (`end_time`),
  KEY ide_create_time (`create_time`)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '秒杀库存表';
DESCRIBE seckill;
SHOW CREATE TABLE seckill;

SELECT * FROM seckill;
INSERT INTO seckill (name, number, start_time, end_time)
VALUES ('2000元秒杀iPhone7', 100, '2017-10-21 18:00:00', '2017-10-21 20:00:00');
UPDATE seckill SET number = 25 WHERE seckill_id = 1003;

INSERT INTO seckill (name, number, start_time, end_time)
VALUES
  ('2000元秒杀iPhone7', 100, '2017-10-21 18:00:00', '2017-10-21 20:00:00'),
  ('1000元秒杀ipad pro', 50, '2017-10-21 19:00:00', '2017-10-21 21:00:00'),
  ('500元秒杀小米3', 80, '2017-10-21 17:00:00', '2017-10-21 18:00:00'),
  ('1500元秒杀irobot980', 20, '2017-10-21 18:00:00', '2017-10-21 20:00:00'),
  ('700元秒杀1000元抵用券', 30, '2017-10-21 16:00:00', '2017-10-21 17:00:00'),
  ('2500元秒杀ipad pro 12寸屏', 10, '2017-10-21 18:00:00', '2017-10-21 20:00:00'),
  ('100元秒杀300元抵用券', 100, '2017-10-21 18:00:00', '2017-10-21 20:00:00'),
  ('1元秒杀红心蜜柚', 150, '2017-10-21 18:00:00', '2017-10-21 20:00:00');

# 秒杀成功明细表
# 用户登录认证相关的信息
CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标示:-1:无效 0:成功 1:已付款',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`, `user_phone`), /*联合主键，一个用户只能对一个商品秒杀*/
  KEY idx_create_time (`create_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
DESCRIBE success_killed;

SHOW CREATE TABLE success_killed;