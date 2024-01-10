package com.icbc.match.exception;

import com.icbc.match.entry.ResultCode;

/**
 * 通用交易异常
 */
public class TransactionException extends RuntimeException {

    private String errorCode;
    private String errorMsg;

    public TransactionException(ResultCode code) {
        super(code.getMessage());
        this.errorCode = String.valueOf(code.getCode());
        this.errorMsg = code.getMessage();
    }

    public TransactionException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
