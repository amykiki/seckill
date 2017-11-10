//存放主要交互逻辑js代码
//javascript 模块化

var seckill = {
    //封装秒杀相关url
    url : {

    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (param) {
            // 手机验证和登录，计时交互
            // 规划交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = param['startTime'];
            var endTime = param['endTime'];
            var seckillId = param['seckillId'];
            //验证手机号
            if(!seckill.validatePhone(killPhone)){
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show: true, //显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false //按esc不会关闭modal，默认为true
                });
                $("#killPhoneBtn").click(function () {
                    var phone = $("#killPhoneKey").val();
                    if(seckill.validatePhone(phone)) {
                        //
                        $.cookie("killPhone", phone, {expire: 7, path: '/seckill'});
                        window.location.reload();
                    }

                });

            }
        }
    },
    //验证手机号
    validatePhone: function (phone) {
        var patt = /^1\d{10}$/;
        if(phone && patt.test(phone)){
            return true;
        }
        return false;
    }
}