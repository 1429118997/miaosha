package com.lizhihai.miaosha.exception;

import com.lizhihai.miaosha.vo.param.CodeMsg;

public class GlobalException extends RuntimeException {
    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

    public void setCm(CodeMsg cm) {
        this.cm = cm;
    }
}
