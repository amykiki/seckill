//存放主要交互逻辑js代码
//javascript 模块化

var seckill = {
    //封装秒杀相关url
    url: {
        timenow: function () {
            return "/seckill/time/now";
        },
        exposer: function (seckillId) {
            return "/seckill/" + seckillId + "/exposer";
        },
        execution: function (seckillId, md5) {
            return "/seckill/" + seckillId+ "/" + md5 + "/execution";
        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (param) {
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            // 手机验证和登录
            if (!seckill.validatePhone(killPhone)) {
                seckill.inputPhone();
            }
            //已经登录规划交互流程
            var startTime = param['startTime'];
            var endTime = param['endTime'];
            var seckillId = param['seckillId'];
            //计时交互
            $.ajax(seckill.url.timenow(), {
                success: function (result) {
                    if(result && result.success) {
                        seckill.countDown(seckillId, result.data, startTime, endTime);
                    }else {
                        setTimeout(function () {
                            window.location.reload();
                        }, 5000);
                    }
                }
            });

        }
    },
    //验证手机号
    validatePhone: function (phone) {
        var patt = /^1\d{10}$/;
        if (phone && patt.test(phone)) {
            return true;
        }
        return false;
    },
    //模拟登录输入手机号，并把手机号存入cookie中
    inputPhone: function () {
        var killPhoneModal = $("#killPhoneModal");
        killPhoneModal.modal({
            show: true, //显示弹出层
            backdrop: 'static', //禁止位置关闭
            keyboard: false //按esc不会关闭modal，默认为true
        });
        $("#killPhoneBtn").click(function () {
            var phone = $("#killPhoneKey").val();
            if (seckill.validatePhone(phone)) {
                //电话写入cookie，有效期7天
                $.cookie("killPhone", phone, {expires: 7, path: '/seckill'});
                //重新刷新页面
                window.location.reload();
            } else {
                //todo 错误文案信息抽取到前端字典里
                $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
            }
        });
    },

    countDown: function (seckillId, serverTime, startTime, endTime) {
        console.log(seckillId + "-" + serverTime + "-" + startTime + "-" + endTime);
        var seckillBox = $("#seckill-box");
        if(serverTime > endTime) {
            seckillBox.html("秒杀结束!");
        } else if(serverTime < startTime) {
            //秒杀还没开始，计时事件绑定
            //防止时间偏移
            var date = new Date(startTime + 1000);
            seckillBox.countdown(date).on('update.countdown', function (event) {
                var format = "%-H时 %M分 %S秒";
                if(event.offset.totalDays > 0) {
                    format = '%-D 天 ' + format;
                }
                $(this).html(event.strftime('秒杀倒计时: ' + format));
            }).on('finish.ocuntdown', function (event) {
                seckill.handlerSeckill(seckillBox, seckillId);
            });

        } else {
            //秒杀进行中
            seckill.handlerSeckill(seckillBox, seckillId);
        }
    },

    handlerSeckill: function (node, seckillId) {
        //1.创建秒杀按钮
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');

        //2获取秒杀连接
        $.post(seckill.url.exposer(seckillId), function (result) {
            if(result && result.success) {
                var exposer = result.data;
                if(exposer.exposable) {
                    node.show();
                    var md5 = exposer.md5;
                    $("#killBtn").one('click', function () {
                        $(this).addClass('disabled');
                        var phone = $.cookie('killPhone');
                        if(!seckill.validatePhone(phone)){
                            window.location.reload();
                            return;
                        }
                        var url = seckill.url.execution(seckillId, md5);
                        var executeResult;
                        console.log("executeUrl " + url);
                        $.ajax(url, {
                            type: 'post',
                            timeout: 5000,
                            error: function (xhr, textStatus) {
                                if(textStatus == 'timeout'){
                                    executeResult =  "太挤了，请稍后再试";
                                }
                            },
                            success:function (result) {
                                if(result) {
                                    if(result.success) {
                                        executeResult = result.data.stateInfo;
                                    }else {
                                        //没有登录
                                        window.location.reload();
                                        return;
                                    }
                                }
                            },
                            complete:function () {
                                //显示秒杀结果
                                node.html('<span class="label label-success">' + executeResult+ '</span>');
                            }
                        });

                    });
                }else {
                    //秒杀未开始,浏览器计时偏差
                    seckill.countDown(seckillId, exposer.now, exposer.start, exposer.end);
                }
            }
        });

    }
};