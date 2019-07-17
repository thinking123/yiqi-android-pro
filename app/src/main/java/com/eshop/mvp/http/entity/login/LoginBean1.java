package com.eshop.mvp.http.entity.login;


import lombok.Getter;
import lombok.Setter;

/**
 * @Author shijun
 * @Data 2019/1/11
 * @Package com.eshop.mvp.http.entity.login
 **/
@Getter
@Setter
public class LoginBean1 {
    private String applyMsg;
    private String blockReason;
    private String blockTime;
    private String blockTimeString;
    private int collectProductNum;
    private int collectStoreNum;
    private String deviceId;
    public int id;
    private int isMonth;
    private String isRegister;
    private String logo;
    private String monthMsg;
    private String nickNmae;
    private String openId;
    private String passWord;
    private String phone;
    private int sex;
    private int state;
    private int storeId;
    private String token;

}
