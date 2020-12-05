package com.lizhihai.miaosha.vo.entity;

import com.lizhihai.miaosha.vo.param.CodeMsg;

public class Result<T> {
    private int code;
    private String msg;
    private T date;




    public static <T> Result<T> success(T date){
         return new Result<T>(date);
    }

    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg==null){
            return;
        }
        this.code=codeMsg.getCode();
        this.msg=codeMsg.getMsg();
    }

    private Result(T date) {
        this.code=200;
        this.msg="运行成功";
        this.date=date;
    }

    private Result(int code, String msg, T date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getDate() {
        return date;
    }
}
