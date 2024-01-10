package com.icbc.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icbc.match.entity.AccountUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Mapper
@Component
public interface AccountUserMapper extends BaseMapper<AccountUser> {

    @Update("UPDATE account_user t  " +
            "SET t.cert_id = #{certId}, " +
            "t.cust_name = #{custName}, " +
            "t.create_time = NOW(), " +
            "t.image_idcard_emblem = #{imageIdcardEmblem}, " +
            "t.image_idcard_face =  #{imageIdcardFace}, " +
            "t.sign_date =  #{signDate}, " +
            "t.validity_period =  #{validityPeriod}" +
            "WHERE " +
            "t.user_id = #{userId}")
    void saveIdcardInfo(AccountUser accountUser);


    @Update("UPDATE account_user t  " +
            "SET t.face_photo = #{facePhoto} " +
            "WHERE " +
            "t.user_id = #{userId}")
    void saveFacePhoto(@Param("facePhoto") String facePhoto, @Param("userId") String userId);


    /**
     * 保存客户的开卡流水号
     *
     * @param origSerno
     * @param userId
     */
    @Update("UPDATE account_user t  " +
            "SET t.orig_serno = #{origSerno} " +
            "WHERE " +
            "t.user_id = #{userId}")
    void saveOrigSerno(@Param("origSerno") String origSerno, @Param("userId") String userId);


    /**
     * 查询客户信息
     *
     * @param userId
     */
    @Select("SELECT t.cust_name,c.medium_id FROM account_user t " +
            "inner join   account_card c on t.user_id= c.user_id " +
            "where t.user_id = #{userId} and t.status = '0'")
    ArrayList<AccountUser> selectUserList(@Param("userId") String userId);
}
