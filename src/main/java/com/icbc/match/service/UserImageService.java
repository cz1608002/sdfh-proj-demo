package com.icbc.match.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service("icbcSet")
@Slf4j
public class UserImageService {

   /* @Autowired
    PersonalProofcheckV1Service personalProofcheckV1Service;

    @Autowired
    CertPersonalEntityEntityauthV1Service certPersonalEntityEntityauthV1Service;

    @Resource(name ="icbc")
    SettlementAccountOpenRealtimeimgUploadV1Service settlementAccountOpenRealtimeimgUploadV1Service;

    @Autowired
    AccountUserMapper accountUserMapper;

    @Autowired
    AccountUserService accountUserService;

    @Autowired
    private SnowflakeIdService snowflakeIdService;

    *//**
     * 进行身份证图像识别
     *
     * @param imageIdCardFace   人脸面图片
     * @param imageIdCardEmblem 国徽面图片
     * @return
     *//*
    public RetEntry proofCheck(MultipartFile imageIdCardFace, MultipartFile imageIdCardEmblem) {

        AccountUser accountUser = new AccountUser();


        String imageIdCardFace64Str = MultipartFileUtil.multipartFileToBase64(imageIdCardFace);
        String imageIdCardEmblem64Str = MultipartFileUtil.multipartFileToBase64(imageIdCardEmblem);

        PersonalProofcheck personalProofcheckFace = new PersonalProofcheck();
        personalProofcheckFace.setChannel("2");
        personalProofcheckFace.setReqId(snowflakeIdService.nextId());
        personalProofcheckFace.setCaseId("2");
        personalProofcheckFace.setImageFile(imageIdCardFace64Str);
        IDCardEntity faceCard = personalProofcheckV1Service.personalProofcheck(personalProofcheckFace);

        String userID = TokenUtil.getUserId();

        MultipartFileUtil.save(imageIdCardFace, userID + "_" + imageIdCardFace.getOriginalFilename());


        PersonalProofcheck personalProofcheckEmblem = new PersonalProofcheck();
        personalProofcheckEmblem.setChannel("2");
        personalProofcheckEmblem.setReqId(snowflakeIdService.nextId());
        personalProofcheckEmblem.setCaseId("3");
        personalProofcheckEmblem.setImageFile(imageIdCardEmblem64Str);
        IDCardEntity emblemCard = personalProofcheckV1Service.personalProofcheck(personalProofcheckEmblem);

        MultipartFileUtil.save(imageIdCardEmblem, userID + "_" + imageIdCardEmblem.getOriginalFilename());

        accountUser.setCustName(faceCard.getName());
        accountUser.setCertType("1");
        accountUser.setCertId(faceCard.getCode());
        accountUser.setImageIdcardFace(userID + "_" + imageIdCardFace.getOriginalFilename());
        accountUser.setImageIdcardEmblem(userID + "_" + imageIdCardEmblem.getOriginalFilename());
        accountUser.setUserId(userID);
        accountUser.setSignDate(emblemCard.getIssuingDate());
        accountUser.setValidityPeriod(emblemCard.getExpringDate());

        accountUserMapper.updateById(accountUser);

        return RetEntry.getOneOkRetEntry().addParam("", "");
    }

    *//**
     * 比对现场人脸图片，进行活体认证
     *
     * @param photo 现场人脸图片
     * @return
     *//*
    public RetEntry entityEntityauth(MultipartFile photo) {

        String userId = TokenUtil.getUserId();


        String photoDataStr = MultipartFileUtil.multipartFileToBase64(photo);

        AccountUser accountUser = accountUserService.getUserInfoByUserId(userId);

        CertPersonalEntityEntityauth certPersonalEntityEntityauth = new CertPersonalEntityEntityauth();
        certPersonalEntityEntityauth.setReqId(snowflakeIdService.nextId());
        certPersonalEntityEntityauth.setChannel("4");
        certPersonalEntityEntityauth.setName(accountUser.getCustName());
        certPersonalEntityEntityauth.setIdCode(accountUser.getCertId());
        certPersonalEntityEntityauth.setPhotoData(photoDataStr);
        certPersonalEntityEntityauth.setScene("02");

        Map result = certPersonalEntityEntityauthV1Service.execute(certPersonalEntityEntityauth);
        if (!"0".equals(result.get("return_code"))) {
            throw new TransactionException("-1", "实体认证失败");
        }

        MultipartFileUtil.save(photo, userId + "_" + photo.getOriginalFilename());

        accountUser.setImageUserFace(userId + "_" + photo.getOriginalFilename());

        accountUserMapper.saveFacePhoto(userId + "_" + photo.getOriginalFilename(), userId);

        //实体认证成功调用实时影像上传
        realtimeimgUpload(accountUser);

        return RetEntry.getOneOkRetEntry().addParam("", "");
    }


    *//**
     * 比对成功后进行图像实时上送
     *
     * @param accountUser
     * @return
     *//*
    public RetEntry realtimeimgUpload(AccountUser accountUser) {

        //本地图片转base64编码
        //人脸
        String frontImgBase64 = ImageToBasse64Util.ImageToBase64ByLocal(accountUser.getImageIdcardFace());
        //国徽
        String backImgBase64 = ImageToBasse64Util.ImageToBase64ByLocal(accountUser.getImageIdcardEmblem());
        //实时图片
        String faceImgBase64 = ImageToBasse64Util.ImageToBase64ByLocal(accountUser.getImageUserFace());
        SettlementAccountBranchRealtimeimgUpload settlementAccountBranchRealtimeimgUpload = new SettlementAccountBranchRealtimeimgUpload();
        settlementAccountBranchRealtimeimgUpload.setCorpNo("corpInst1234");
        settlementAccountBranchRealtimeimgUpload.setTrxAccDate(DateUtil.formatDate(DateUtil.date()));
        settlementAccountBranchRealtimeimgUpload.setTrxAccTime(DateUtil.formatTime(DateUtil.date()));
        settlementAccountBranchRealtimeimgUpload.setCorpSerno("13535536");
        settlementAccountBranchRealtimeimgUpload.setOutServiceCode("uploadimg");
        settlementAccountBranchRealtimeimgUpload.setCertNo(accountUser.getCertId());
        settlementAccountBranchRealtimeimgUpload.setCustName(accountUser.getCustName());
        settlementAccountBranchRealtimeimgUpload.setValidityPeriod(accountUser.getValidityPeriod());
        settlementAccountBranchRealtimeimgUpload.setFrontImgType(ImgTypeUtil.getImgType(accountUser.getImageIdcardFace()));
        settlementAccountBranchRealtimeimgUpload.setFrontImg(frontImgBase64);
        settlementAccountBranchRealtimeimgUpload.setBackImgType(ImgTypeUtil.getImgType(accountUser.getImageIdcardEmblem()));
        settlementAccountBranchRealtimeimgUpload.setBackImg(backImgBase64);
        settlementAccountBranchRealtimeimgUpload.setSecretKey("");
        settlementAccountBranchRealtimeimgUpload.setClearFlag(0);
        settlementAccountBranchRealtimeimgUpload.setFaceImgType(ImgTypeUtil.getImgType(accountUser.getImageUserFace()));
        settlementAccountBranchRealtimeimgUpload.setFaceImg(faceImgBase64);

        Map result = settlementAccountOpenRealtimeimgUploadV1Service.execute(settlementAccountBranchRealtimeimgUpload);

        if (!"0".equals(result.get("return_code"))) {
            throw new TransactionException("-1", "实时影像上传失败");
        }
        return RetEntry.getOneOkRetEntry();
    }

*/
}
