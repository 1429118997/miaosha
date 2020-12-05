package com.lizhihai.miaosha.vo.param;

public class CodeMsg {

    private int code;
    private String msg;

    public static CodeMsg SUCCESS=new CodeMsg(200,"成功");


    /*服务器内部错误*/
    public static CodeMsg SERVER_ERROR=new CodeMsg(500100,"服务器错误");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101,"参数校验异常：%s");
    public static CodeMsg SESSION_EXPIRE=new CodeMsg(500102,"session失效，请重新登录");
    public static CodeMsg REQUIRE_ILLEGA=new CodeMsg(500103,"非法请求");
    public static CodeMsg VERIFYCODE_ERROR=new CodeMsg(500104,"验证码错误");
    public static CodeMsg ACCESS_LIMIT=new CodeMsg(500105,"访问过于频繁！");

    /*登录模块错误*/
    public static CodeMsg PASSWORD_EMPTY=new CodeMsg(500200,"密码不能为空");
    public static CodeMsg MOBILE_EMPTY=new CodeMsg(500201,"密码不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500201,"手机格式不能正确");
    public static CodeMsg USER_EXIT = new CodeMsg(500202,"用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500203,"密码错误");
    public static CodeMsg MOBOLE_BIND_ERROR = new CodeMsg(5002004,"手机号码对应id不存在");
    public static CodeMsg PASSWORD_UPDATE=new CodeMsg(500205,"密码更新失败");
    public static CodeMsg USER_NOT_LOGIN=new CodeMsg(500206,"用户没登录");

    /*订单模块错误*/
    public static CodeMsg ORDERINFO_NOT_EXIST=new CodeMsg(500301,"订单不存在");


    /*秒杀模块5005XX*/
    public static CodeMsg MIAOSHA_OVER = new CodeMsg(500500,"商品秒杀完毕");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501,"商品已经秒杀过");

    private  CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillAllArgs(Object...objects){
        int code=this.code;
        String msg=String.format(this.msg, objects);
        return new CodeMsg(code,msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
