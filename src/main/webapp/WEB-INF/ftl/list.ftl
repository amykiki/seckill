<!doctype html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>秒杀列表</title>
</head>
<body>
<#list seckillLists as seckill>
    <li>${seckill.seckillId} - ${seckill.name}</li>
</#list>
</body>
</html>