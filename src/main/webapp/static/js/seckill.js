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
            /**
             * 手机验证和登录，计时交互
             * 规划交互流程
             */

            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = param['startTime'];
            var endTime = param['endTime'];
            var seckillId = param['seckillId'];
        }
    },
    //验证手机号
    validatePhone: function (phone) {
        if(phone && phone.length == 11)
    }
}