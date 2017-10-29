<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>秒杀列表</title>
    <#include "common/head.ftl">
</head>
<body>
<#--页面部分-->
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <td>名称</td>
                        <td>库存</td>
                        <td>开始时间</td>
                        <td>结束时间</td>
                        <td>创建时间</td>
                        <td>详情页</td>
                    </tr>
                </thead>
                <tbody>
                    <#list seckillLists as seckill>
                        <tr>
                            <td>${seckill.name}</td>
                            <td>${seckill.number}</td>
                            <td>${seckill.startTime?datetime}</td>
                            <td>${seckill.endTime?datetime}</td>
                            <td>${seckill.createTime?datetime}</td>
                            <td>
                                <a class="btn btn-info" href="/seckill/${seckill.seckillId}/detail" target="_blank">
                                    详情页
                                </a>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<#include "common/end.ftl">
</html>