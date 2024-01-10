package com.icbc.match.utils;

public class ImgTypeUtil {

    public static String getImgType(String fileName) {

        String imgType = "";

        switch (fileName.substring(fileName.lastIndexOf("."))) {
            case "jpg":
                imgType = "01";
                break;
            case "png":
                imgType = "02";
                break;
            case "jpeg":
                imgType = "03";
                break;
            case "bmp":
                imgType = "04";
                break;
            default:
                imgType = "05";
        }
        return imgType;

    }
}
