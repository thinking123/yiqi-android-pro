package com.eshop.mvp.http.entity.order;

public class RefundDetailUser {
    /**
     * applyTime : string
     * id : 0
     * nsigneeAdress : string
     * nsigneeName : string
     * nsigneePhone : string
     * orderId : string
     * platformPhone : string
     * refundReason : string
     * remainingTime : string
     * storePhone : string
     * totalPrice : string
     */

    private String applyTime;
    private int id;
    private String nsigneeAdress;
    private String nsigneeName;
    private String nsigneePhone;
    private String orderId;
    private String platformPhone;
    private String refundReason;
    private String remainingTime;
    private String storePhone;
    private String totalPrice;

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

    public String getNsigneeAdress() {
        return nsigneeAdress;
    }

    public void setNsigneeAdress(String nsigneeAdress) {
        this.nsigneeAdress = nsigneeAdress;
    }

    public String getNsigneeName() {
        return nsigneeName;
    }

    public void setNsigneeName(String nsigneeName) {
        this.nsigneeName = nsigneeName;
    }

    public String getNsigneePhone() {
        return nsigneePhone;
    }

    public void setNsigneePhone(String nsigneePhone) {
        this.nsigneePhone = nsigneePhone;
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
