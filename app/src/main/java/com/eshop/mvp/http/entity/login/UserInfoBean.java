package com.eshop.mvp.http.entity.login;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/11
 * @Package com.eshop.mvp.http.entity.login
 **/
@Getter
@Setter
public class UserInfoBean implements Serializable {

    private String phone;

    private String openId;

    private String passWord;

    private String deviceId;

    private String logo;

    private String nickName;

    private String sex;

    private int state;

    private String applyMsg;

    private int isMonth;

    private String monthMsg;

    private String blockTime;

    private String blockTimeString;

    private String blockReason;

    private String isRegister;

    private String collectStoreNum;

    private String collectProductNum;

    private String storeId;

    private String token;

}
