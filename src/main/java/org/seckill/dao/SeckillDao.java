package org.seckill.dao;

import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    /**
     * 减库存，秒杀成功
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新记录
     */
    int reduceNumer(Long seckillId, Date killTime);

    /**
     * 根据id查询秒杀库存
     * @param seckillId
     * @return
     */
    Seckill queryById(Long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset 偏移量
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offset, int limit);
}
