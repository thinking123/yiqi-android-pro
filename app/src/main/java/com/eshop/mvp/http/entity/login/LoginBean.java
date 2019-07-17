package com.eshop.mvp.http.entity.login;


/**
 * @Author shijun
 * @Data 2019/1/11
 * @Package com.eshop.mvp.http.entity.login
 **/

public class LoginBean {

    /**
     * applyMsg :
     * blockReason :
     * blockTime :
     * blockTimeString :
     * collectProductNum : 1
     * collectStoreNum : 1
     * deviceId :
     * id : 1
     * isMonth : 1
     * isRegister :
     * logo :
     * monthMsg :
     * nickNmae :
     * openId :
     * passWord :
     * phone :
     * sex : 1
     * state : 1
     * storeId : 1
     * token :
     *
     *
     * //环信Id
     * huanxinId
     */

    private String applyMsg;
    private String blockReason;
    private String blockTime;
    private String blockTimeString;
    private int collectProductNum;
    private int collectStoreNum;
    private String deviceId;
    private int id;
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
    //环信Id
    private String huanxinId;
    //环信 聊天室Id
    private String chatRoomId;

//    private String nickNmae;


    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getHuanxinId() {
        return huanxinId;
    }

    public void setHuanxinId(String huanxinId) {
        this.huanxinId = huanxinId;
    }
//    public String getHuanxinId(){
//        return huanxinId;
//    }
//
//    public void setHuanxinId(String id){
//        huanxinId = id;
//    }

    public String getApplyMsg() {
        return applyMsg;
    }

    public void setApplyMsg(String applyMsg) {
        this.applyMsg = applyMsg;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public String getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(String blockTime) {
        this.blockTime = blockTime;
    }

    public String getBlockTimeString() {
        return blockTimeString;
    }

    public void setBlockTimeString(String blockTimeString) {
        this.blockTimeString = blockTimeString;
    }

    public int getCollectProductNum() {
        return collectProductNum;
    }

    public void setCollectProductNum(int collectProductNum) {
        this.collectProductNum = collectProductNum;
    }

    public int getCollectStoreNum() {
        return collectStoreNum;
    }

    public void setCollectStoreNum(int collectStoreNum) {
        this.collectStoreNum = collectStoreNum;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(int isMonth) {
        this.isMonth = isMonth;
    }

    public String getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(String isRegister) {
        this.isRegister = isRegister;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMonthMsg() {
        return monthMsg;
    }

    public void setMonthMsg(String monthMsg) {
        this.monthMsg = monthMsg;
    }

    public String getNickNmae() {
        return nickNmae;
    }

    public void setNickNmae(String nickNmae) {
        this.nickNmae = nickNmae;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
