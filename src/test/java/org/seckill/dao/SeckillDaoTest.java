package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test，junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;
    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(2, 4);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }

        assertEquals((Long)1002L, seckills.get(0).getSeckillId());
        assertEquals((Long)1005L, seckills.get(3).getSeckillId());
    }

    @Test
    public void testReduceNumer() throws Exception {
        Long seckillId = 1000L;
        //1 killTime在开始时间之前
        Date killTime1 = new Date(System.currentTimeMillis() - 60 * 60 * 1000);
        System.out.println("killTime1 = " + killTime1);
        int update1 = seckillDao.reduceNumer(seckillId, killTime1);
        assertEquals(0, update1);
        //2 killTime在结束时间之后
        Date killTime2 = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
        System.out.println("killTime2 = " + killTime2);
        int update2 = seckillDao.reduceNumer(seckillId, killTime2);
        assertEquals(0, update2);
        //3 killTime在开始和结束时间之内
        Date killTime3 = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
        System.out.println("killTime3 = " + killTime3);
        int update3 = seckillDao.reduceNumer(seckillId, killTime3);
        assertEquals(1, update3);

    }

    @Test
    public void testQueryById() throws Exception {
        Long seckillId = 1000L;
        Seckill seckill = seckillDao.queryById(seckillId);
        System.out.println("seckill = " + seckill);
        assertEquals((Long) 1000L, seckill.getSeckillId());

    }

}