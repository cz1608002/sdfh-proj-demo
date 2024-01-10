package com.icbc.match.utils;


import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;


public class MultipartFileUtil {

    public static File multipartFileToFile(MultipartFile file, String filePath) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(filePath);
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String multipartFileToBase64(MultipartFile file) {
        String base64Str = "";
        try {
            base64Str = Base64.encodeBase64String(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64Str;
    }


    public static void save(MultipartFile file, String fileName) {
        String filePath = "/opt/data/" + fileName;
        File desFile = new File(filePath);
        if (!desFile.getParentFile().exists()) {
            desFile.mkdirs();
        }
        try {
            file.transferTo(desFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return;

    }


    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
