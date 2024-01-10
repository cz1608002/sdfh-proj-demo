package com.icbc.match.utils;


import com.icbc.match.entry.Result;
import com.icbc.match.exception.TransactionException;

public class CheckResultUtil {

    public static void checkResult(Result result) {
        if (!"0".equals(result.getRetCode())) {
            throw new TransactionException(result.getRetCode(), result.getRetMsg());
        }
        return;
    }
}
