package com.eshop.mvp.http.entity.order;

public class RefundDetail {
    /**
     * applyTime : string
     * id : 0
     * orderId : string
     * platformPhone : string
     * refundReason : string
     * remainingTime : string
     * storeId : 0
     * storePhone : string
     * totalPrice : string
     */

    private String applyTime;
    private int id;
    private String orderId;
    private String platformPhone;
    private String refundReason;
    private String remainingTime;
    private int storeId;
    private String storePhone;
    private String totalPrice;

    //环信Id
    private String huanxinId;

    private String storeImg;

    public String getStreoName() {
        return streoName;
    }

    public void setStreoName(String streoName) {
        this.streoName = streoName;
    }

    private String streoName;

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public String getHuanxinId() {
        return huanxinId;
    }

    public void setHuanxinId(String huanxinId) {
        this.huanxinId = huanxinId;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlatformPhone() {
        return platformPhone;
    }

    public void setPlatformPhone(String platformPhone) {
        this.platformPhone = platformPhone;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
