package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
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
    int reduceNumer(@Param("seckillId") Long seckillId, @Param("killTime") Date killTime);

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
    //java没有保存形参的记录，queryAll(int offset, int limit) -> queryAll(arg0, arg1)
    //因此有多个参数的时候，需要标识参数的名字
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
