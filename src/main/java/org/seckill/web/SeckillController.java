package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
//模块 url:/模块/资源/{id}/细分
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SeckillService seckillService;

    //requestMapping中多于一个元素时，要用value才能映射url，用name不行
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> seckills = seckillService.getSeckillList();
        model.addAttribute("seckillLists", seckills);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return redirectTo("/seckill/list");
        }
        Seckill seckill = seckillService.getSeckillById(seckillId);
        if (seckill == null) {
            return redirectTo("/seckill/list");
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    //json
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId) {
        SeckillResult<Exposer> exposerResult;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            exposerResult = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            exposerResult = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return exposerResult;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long phone) {
        if (!validatePhone(phone)) {
            return new SeckillResult<SeckillExecution>(false, "未登录");
        }
        SeckillResult<SeckillExecution> result;
        SeckillExecution execution;
        try {
            execution = seckillService.excuteSeckill(seckillId, phone, md5);
            result = new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e) {
            execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(true, execution, SeckillStatEnum.REPEAT_KILL.getStateInfo());
        } catch (SeckillCloseException e) {
            execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            result = new SeckillResult<SeckillExecution>(true, execution, SeckillStatEnum.END.getStateInfo());
        } catch (SeckillException e) {
            logger.error(e.getMessage(), e);
            execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(true, execution, SeckillStatEnum.INNER_ERROR.getStateInfo());
        }
        return result;
    }

    @RequestMapping(value = "/time/now",
            method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult<Long>(true, now.getTime());
    }

    private String redirectTo(String url) {
        return "redirect:" + url;
    }

    private String forwardTo(String url) {
        return "forward:" + url;
    }

    private boolean validatePhone(Long phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        String phoneStr = String.valueOf(phone);
        Pattern pattern = Pattern.compile("^1\\d{10}$");
        Matcher matcher = pattern.matcher(phoneStr);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

}
