package org.seckill.dao;

import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {

    /**
     * 插入购买明细，可以过滤重复(建表的时候使用联合主键 seckillId + userPhone)
     * @param seckillId
     * @param userPhone
     * @return 插入的行数，返回0代表插入失败
     */
    int insertSuccessKilled(Long seckillId, Long userPhone);

    /**
     * 根据id查询successKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(Long seckillId);


}
