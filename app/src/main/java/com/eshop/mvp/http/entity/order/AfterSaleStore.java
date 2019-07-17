package com.eshop.mvp.http.entity.order;

import java.util.List;

public class AfterSaleStore {

    /**
     * applyTime : string
     * id : 0
     * nsigneeAdress : string
     * nsigneeName : string
     * nsigneePhone : string
     * orderId : string
     * refundReason : string
     * totalPrice : string
     * voucher : ["string"]
     */

    private String applyTime;
    private int id;
    private String nsigneeAdress;
    private String nsigneeName;
    private String nsigneePhone;
    private String orderId;
    private String refundReason;
    private String totalPrice;
    private List<String> voucher;
    //环信Id
    private String huanxinId;

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

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<String> getVoucher() {
        return voucher;
    }

    public void setVoucher(List<String> voucher) {
        this.voucher = voucher;
    }
}
