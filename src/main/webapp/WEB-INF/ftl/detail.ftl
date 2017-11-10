<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>秒杀${seckill.seckillId}详情</title>
    <#include "common/head.ftl">
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <#--显示time图标-->
                <span class="glyphicon glyphicon-time"></span>
                <#--展示倒计时-->
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        ${seckill.seckillId} -- ${seckill.name} -- ${seckill.startTime?datetime} -- ${seckill.endTime?datetime}
        </div>
    </div>
</div>
<#--登录弹出层，输出电话-->
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="填手机号o(^▽^)o" class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <#--验证信息-->
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    submit
                </button>
            </div>
        </div>
    </div>
</div>
</body>
<#include "common/end.ftl">
<#--jquery cookie操作插件-->
<script src="${my.jsLib}/jquery.cookie-1.4.js"></script>
<#--jquery countDown倒计时插件-->
<script src="${my.jsLib}/jquery.plugin.min.js"></script>
<script src="${my.jsLib}/jquery.countdown-2.1.0.js"></script>
<script src="${my.jsRoot}/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
   $(function () {
        //传入参数
        seckill.detail.init({
            seckillId: ${seckill.seckillId},
            startTime: ${seckill.startTime?long},
            endTime: ${seckill.endTime?long}
        });
    });
</script>
</html>