package com.icbc.match.controller;

import com.icbc.match.entry.Result;
import com.icbc.match.entry.RetEntry;
import com.icbc.match.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class AccountImageController {

 /*   @Autowired
    private UserImageService userImageService;


    *//**
     * 身份证识别
     *
     * @param imageIdCardFace   人像图片
     * @param imageIdCardEmblem 国徽图片
     * @return
     *//*
    @PostMapping("/proof/check")
    public RetEntry idCardImgUpload(@RequestParam(name = "id") MultipartFile imageIdCardFace,
                                    @RequestParam(name = "age") MultipartFile imageIdCardEmblem) {

        return userImageService.proofCheck(imageIdCardFace, imageIdCardEmblem);
    }

    *//**
     * @param faceImage 个人图像，用于实体核验，成功后上传图片
     * @return
     *//*
    @PostMapping("/entity/entityauth")
    public RetEntry entityauth(@RequestParam(name = "faceImage") MultipartFile faceImage) {

        return userImageService.entityEntityauth(faceImage);
    }*/
}
