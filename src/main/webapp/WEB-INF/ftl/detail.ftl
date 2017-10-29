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
        ${seckill.seckillId} -- ${seckill.name} -- ${seckill.startTime?datetime} -- ${seckill.endTime?datetime}
        </div>
    </div>
</div>
</body>
<#include "common/end.ftl">
</html>