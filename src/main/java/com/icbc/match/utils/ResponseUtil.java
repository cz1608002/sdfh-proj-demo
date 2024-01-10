package com.icbc.match.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by meng on 2018/6/9.
 */
public class ResponseUtil {

    public static void writeResponse(HttpServletResponse response, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        OutputStream os = response.getOutputStream();
        os.write(content.getBytes("utf-8"));
        os.flush();
        os.close();
    }
}
