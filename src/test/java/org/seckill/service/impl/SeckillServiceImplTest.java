package org.seckill.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillService seckillService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        assertEquals(8, (int)seckillList.size());
    }

    @Test
    public void testGetSeckillById() throws Exception {
        Seckill seckill = seckillService.getSeckillById(1005L);
        logger.info("seckill={}", seckill);
        assertEquals((Long)1005L, seckill.getSeckillId());
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        //1.查询日期已经过期
        Long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}", exposer);
        assertEquals(false, exposer.isExposable());
        //2.查询日期还没有到
        seckillId = 1001L;
        exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}", exposer);
        assertEquals(false, exposer.isExposable());
        //3.查询日期合法
        seckillId = 1002L;
        exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}", exposer);
        assertEquals(true, exposer.isExposable());
    }

    @Test
    public void testExcuteSeckill() throws Exception {
        Long seckillId = 1002L;
        Long userPhone = 18930161001L;
        String md5Wrong = "4ac32129f00c69065bfa5aca76c417c4";
        expectedEx.expect(SeckillException.class);
        expectedEx.expectMessage("seckill data rewrite");
        seckillService.excuteSeckill(seckillId, userPhone, md5Wrong);

    }

    @Test
    public void testExcuteSeckill2() throws Exception {
        Long seckillId = 1002L;
        Long userPhone = 18930161001L;
        expectedEx.expect(SeckillException.class);
        expectedEx.expectMessage("seckill data rewrite");
        seckillService.excuteSeckill(seckillId, userPhone, null);
        String md5Right = "4ac32129f00c69065bfa5aca76c417c6";
        seckillService.excuteSeckill(seckillId, userPhone, md5Right);

    }

    //testExcuteSeckill3和testExcuteSeckill4一起运行，用于测试正常情况和异常情况
    @Test
    public void testExcuteSeckill3() throws Exception {
        Long seckillId = 1002L;
        Long userPhone = 18930161001L;
        String md5Right = "4ac32129f00c69065bfa5aca76c417c6";
        SeckillExecution seckillExecution = seckillService.excuteSeckill(seckillId, userPhone, md5Right);
        logger.info("seckillExecution = {}", seckillExecution);
    }

    @Test
    public void testExcuteSeckill4() throws Exception {
        Long seckillId = 1002L;
        Long userPhone = 18930161001L;
        String md5Right = "4ac32129f00c69065bfa5aca76c417c6";
        expectedEx.expect(RepeatKillException.class);
        expectedEx.expectMessage("seckill repeated");
        seckillService.excuteSeckill(seckillId, userPhone, md5Right);
    }

    @Test
    public void testExcuteSeckill5() throws Exception {
        Long seckillId = 1002L;
        Long userPhone = 18930161001L;
        String md5Right = "4ac32129f00c69065bfa5aca76c417c6";
        expectedEx.expect(SeckillCloseException.class);
        expectedEx.expectMessage("seckill is closed");
        seckillService.excuteSeckill(seckillId, userPhone, md5Right);
    }

}