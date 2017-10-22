package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        Long seckillId = 1000L;
        Long userPhone = 18988889999L;
        //1第一次插入成功
        int update = successKilledDao.insertSuccessKilled(seckillId, userPhone);
        assertEquals(1, update);
        //2第二次插入主键冲突，失败
        update = successKilledDao.insertSuccessKilled(seckillId, userPhone);
        assertEquals(0, update);
    }

    @Test
    public void testQueryByIdithSeckill() throws Exception {
        Long seckillId = 1000L;
        Long userPhone = 18988889999L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
        System.out.println("successKilled = " + successKilled);
        System.out.println(successKilled.getSeckill());
    }

}